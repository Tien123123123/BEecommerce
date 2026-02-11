# Giải thích và Cách khắc phục Lỗi "Row was updated or deleted by another transaction"

Chào bạn, tôi hiểu bạn vẫn đang gặp lỗi này. Dưới đây là giải thích chi tiết bằng tiếng Việt về nguyên nhân và cách tôi đang xử lý nó cho bạn.

## 1. Nguyên nhân (Tại sao lỗi này lại xảy ra?)

Lỗi `Row was updated or deleted by another transaction` (Dòng đã bị cập nhật hoặc xóa bởi một giao dịch khác) xuất hiện khi Hibernate cố gắng cập nhật (`UPDATE`) một bản ghi vào cơ sở dữ liệu (DB), nhưng DB báo lại là "Không có dòng nào được cập nhật cả" (0 rows modified).

Trong trường hợp của bạn (`promoteToSeller`), điều này xảy ra do sự bất đồng bộ giữa trạng thái trong bộ nhớ (Code Java) và trạng thái thực tế dưới Database, đặc biệt là liên quan đến tính năng **Soft Delete** (Xóa mềm).

Cụ thể:
1.  **Trạng thái "Xóa mềm"**: Khi `User` bị xóa và khôi phục, có thể bản ghi `Seller` liên quan vẫn ở trạng thái `soft_delete = true` (đã xóa) trong DB.
2.  **Restore chưa đồng bộ**: Chúng ta đã dùng `restoreById` (Native Query) để chuyển `soft_delete = false`. Tuy nhiên, đối tượng `SellerEntity` đang nằm trong bộ nhớ của Hibernate vẫn "nghĩ" là nó đang bị xóa (`soft_delete = true`).
3.  **Lỗi xảy ra**: Khi bạn gọi lệnh lưu (`save`), Hibernate sinh ra câu lệnh kiểu:
    `UPDATE sellers SET ... WHERE id = 1 AND soft_delete = false`
    Do Hibernate vẫn nghĩ entity cũ bị xóa hoặc do native query chưa được commit ngay, câu lệnh này không tìm thấy dòng nào thỏa mãn -> Bùm! Lỗi `Row was updated or deleted`.

## 2. Giải pháp tôi đã thực hiện

Để khắc phục triệt để, tôi đã điều chỉnh `SellerService.java` theo quy trình nghiêm ngặt sau:

1.  **Dùng Native Query để tìm kiếm**: `findByUserIdIncludingDeleted` để tìm cả seller đã bị xóa.
2.  **Kiểm tra và Khôi phục (Restore)**:
    *   Nếu seller bị `soft_delete`: Gọi `restoreById` để cập nhật DB ngay lập tức.
    *   **QUAN TRỌNG**: Gọi `entityManager.flush()` để đẩy thay đổi xuống DB ngay.
    *   **QUAN TRỌNG**: Gọi `entityManager.refresh(seller)` để tải lại đối tượng `seller` từ DB lên. Lúc này `seller` trong bộ nhớ sẽ biết là mình đã "sống lại" (`soft_delete = false`).
3.  **Lưu Explicit (Rõ ràng)**:
    *   Thay vì để `User` tự động lưu `Seller` (Cascade), tôi gọi `sellerRepository.save(seller)` trực tiếp. Điều này giúp đảm bảo Seller được cập nhật chính xác trước khi gán vào User.

Bạn hãy thử chạy lại API `promoteToSeller` (`PATCH /api/seller/1`) xem sao nhé. Nếu vẫn còn lỗi, hãy gửi lại log mới nhất giúp tôi.
