# Định lý CAP 
## 1. Khái niệm
Trong một hệ thống máy tính phân tán, bạn chỉ có thể hỗ trợ tối đa hai trong số các tính chất sau:
* Consistency (tính nhất quán): Tính nhất quán có nghĩa là dữ liệu giống nhau trên node, do đó bạn có thể đọc hoặc ghi từ / đến bất kỳ node nào và nhận cùng dữ liệu.
* Availability (Tính khả dụng): Mọi yêu cầu đều nhận được phản hồi(kể cả lỗi), mà không đảm bảo được nó có phải là thông tin mới nhất được cập nhật hay không.
* Partition Tolerance (Khả năng chịu lỗi khi phân vùng): Khi một hệ thống được phần chia thành nhiều service, nếu có 1 service bị lỗi thì hệ thống vẫn phải chịu đựng được và hoạt động bình thường.
<div style="text-align:center">
    <img   src="https://camo.githubusercontent.com/13719354da7dcd34cd79ff5f8b6306a67bc18261/687474703a2f2f692e696d6775722e636f6d2f62674c4d4932752e706e67" />
</div>
 *Network không đáng tin cậy, vì vậy hệ thống luôn phải hỗ trợ tính Partition Tolerance, do đó hệ thống phải lựa chọn giữa Consistency-Partition Tolerance(CP) hoặc Availability-Partition Tolerance(AP)*.

### CP - consistency and partition tolerance
Hệ thống sẽ chờ phản hồi từ các node khác, có thể dẫn đến lỗi timeout. Nên chọn CP nếu nhu cầu chủ yếu là read or write.

### AP - availability and partition tolerance
Hệ thống luôn sẵn sàng trả lại phiên bản hiện tại của thông tin, có thể không phải là thông tin mới nhất.
Nên chọn AP nếu nhu cầu chủ yếu là sự **NHẤT QUÁN CUỐI CÙNG** hoặc khi hệ thống cần tiếp tục hoạt đông mặc dù có lỗi từ bên ngoài.
 
## 2. Eventual consistency
Hệ thống cho phép tính nhất quán của dữ liệu không cần phải đảm bảo ngay lập tức sau mỗi thời điểm read hoặc write. Sau khoảng thời gian rất ngắn thì hệ thống sẽ được cập nhật xong dữ liệu và dữ liệu đó là dữ liệu nhất quán cuối cùng.
# Latency vs throughput
## 1. Latency
là thời gian để thực hiện một số hành động hoặc để tạo ra một số kết quả.
## 2. Throughput
là số lượng hành động hoặc kết quả như vậy trên một đơn vị thời gian.

***Ví dụ***

Một dây chuyền lắp ráp và sản xuất xe hơi mất 8 giờ để tạo ra một chiếc xe và nhà máy sản xuất một trăm hai mươi chiếc xe mỗi ngày.

Latency: 8 giờ.

Throughput: 120 xe/ngày.

# Scale database
## 1 Định nghĩa
Là các giải pháp giải quyết khi database mở rộng hoặc được cắt nhỏ sao cho performance không bị ảnh hưởng hoặc ảnh hưởng thấp nhất.

Các phương pháp giải Scale database: master-slave replication, master-master replication, federation, sharding, denormalization, and SQL tuning. 
## 2 Các phương pháp Scale database (MySQL)
### 2.1 Master-slave replication
<div style="text-align:center">
    <img src="https://camo.githubusercontent.com/6a097809b9690236258747d969b1d3e0d93bb8ca/687474703a2f2f692e696d6775722e636f6d2f4339696f47746e2e706e67" />
</div>

* Khái niệm: 

    Master sẽ có khả năng read/write, nhân bản ra 1 hoặc nhiều slave, các slave này chỉ đươc phép đọc. Các slave cũng có thể  tự nhân bản ra các slave khác. Nếu master dừng hoạt động thì hệ thống vẫn tiếp tục hoạt động ở chế độ read-only cho đến khi 1 slave được nâng cấp lên thành master hoặc 1 master mới được cấp phát.

* Khuyết điểm:
    - Bổ sung logic để nâng cấp 1 slave lên master.

### 2.2 Master-master replication
<div style="text-align:center">
    <img src="https://camo.githubusercontent.com/5862604b102ee97d85f86f89edda44bde85a5b7f/687474703a2f2f692e696d6775722e636f6d2f6b7241484c47672e706e67" />
</div>

* Khái niệm:
    Nhân bản thành 2 con master có khả năng read/write. Nếu một trong hai con bị down thì hệ thống vẫn có thể tiếng tục read/write trên con còn lại.


* Khuyết điểm:
    - Cần cân bằng tải đến các server hoặc cần thay đổi application logic để xác định nơi dùng để write.
    - Thường thiếu sự nhất quán(vi phạm ACID) hoặc tăng độ trễ của việc ghi do phải đồng bộ hóa.
    - Conflict nhiều hơn khi thêm nhiều nút được thêm vào hoặc độ trễ tăng lên.

***Khuyết điểm chung của 2 phương pháp replication:***
- Dữ liệu có thể bị mất khi master lỗi trước khi có dữ liệu mới nhất đưtợc ghi vào cần sao chép đến các node khác.
- Việc write trên master sẽ phải sao chép các slave. Nếu write quá nhiều sẽ làm tăng độ trễ của hệ thống.
- Khi việc đọc các slave tăng lên, cần phải tạo ra nhiều bản sao, dẫn đến độ trễ cho replication lớn hơn.
- Trên một số hệ thống, việc ghi vào master có thể sinh ra nhiều luồng để viêt song song, trong khi các bản replicate chỉ hỗ trợ ghi tuần tự với một luồng.
- Replication cần hỗ trợ thêm phần cứng và tăng thêm độ phức tạp.

### 2.3 Federation
<div style="text-align:center">
    <img src="https://camo.githubusercontent.com/6eb6570a8b6b4e1d52e3d7cc07e7959ea5dac75f/687474703a2f2f692e696d6775722e636f6d2f553371563333652e706e67" />
</div>

* Khái niệm:
    Chia nhỏ databases ra thành từng chức năng riêng, ví dụ thay vì 1 databases nguyên khối, bạn có thể chia thành 3 DB: Forums , Users, Products. Khi đó số lượng read/write trên từng DB sẽ ít hơn. Với việc không chỉ tập trung vào 1 DB, bạn có thể write song song các DB.

* Khuyết điểm: 
    - Federation sẽ không hiệu quả nếu hệ thống của bạn có quá nhiều chức năng.
    - Sẽ phải cập nhật logic cho từng DB chức năng có thể read/write.
    - Việc kết hợp dữ liệu từ các DB phức tạp hơn là đỉ thằng vào 1 DB chung.
    - Cần nhiều thiết bị phần cứng và độ phức tạp sẽ cao hơn.

### 2.4 Sharding
<div style="text-align:center">
    <img src="https://camo.githubusercontent.com/1df78be67b749171569a0e11a51aa76b3b678d4f/687474703a2f2f692e696d6775722e636f6d2f775538783549642e706e67" />
</div>

* Khái niệm:
    - Chia nho DB ra thành nhiều phần khác nhau(tiêu chi tự đề ra). Các cấu trúc lưu trữ có thể  giống nhau những dữ liệu sẽ khác nhau. Tương tự với Federation thì Sharing cũng có thể write song song với nhau, tăng được hiệu suất. 
    - Các cách phổ biến để phân đoạn DB users là thông qua tên hoặc vị trí địa lý của users.

* Khuyết điểm:
    - Cần cập nhật logic cho hệ thống để truy cập, điều này có thể dẫn đến câu SQL sẽ phức tạp.
    - Phân phố khối lượng dữ liệu trong DB có thể bị lệch.
    - Tái cần bằng lại lượng dữ liệu trong DB sẽ làm phức tạp thêm hệ thống.
    - Cần nhiều thiết bị phần cứng và độ phức tạp sẽ cao hơn. 

### 2.5 Denormalization
* Khái niệm:
    - Denormalization cố gắng cải thiện tốc độ read. Các bản copy dự phòng được viết ra thành nhiều bảng để tránh việc kết nối kém.
    - Khi dữ liệu được phần phối với các kỹ thuật như federation và sharing thì việc quản lý các kết nối trên các data center sẽ phức tạp hơn. Denormalization sẽ làm giảm đi sự phức tạp này
    - Trên hầu hết các hệ thống, tỉ lệ read có thể lớn hơn write rất nhiều (100:1 có thể lên đến 1000:1). Một số kết quả của việc read trên DB phức tạp có thể rât tốn kém thời gian và disk operation.

* Khuyết điểm:
    - Data bị duplicate.
    - Các ràng buộc có thể làm các bản sao dư thừa của thông tin bị đồng bộ, làm tặng độ phức tạp của dữ liệu.
    - Một số dữ liệu không chuẩn hóa sẽ hoạt động kém hơn khi việc write phức tạp.

### 2.6 SQL tuning
    - Sử dụng CHAR thay vì VARCHAR cho các trường có độ dài cố định (CHAR cho phép truy cập nhanh, ngẫu nhiên, VARCHAR thì phải kết thúc chuỗi mới chuyển sang chuỗi tiếp theo).
    - Sử dụng TEXT cho các khối văn bản lớn như bài đăng trên blog. TEXT cũng cho phép tìm kiếm boolean. Sử dụng TEXT trong việc lưu trữ một con trỏ trên đĩa được sử dụng để định vị khối văn bản.
    - Sử dụng INT cho số lượng lớn hơn lên đến 2 ^ 32 hoặc 4 tỷ.
    - Sử dụng DECIMAL cho tiền tệ để tránh các lỗi biểu diễn dấu phẩy động.
    - Tránh lưu trữ BLOBS lớn, lưu trữ vị trí của nơi để lấy đối tượng thay thế.
    - VARCHAR (255) là số ký tự lớn nhất có thể được đếm trong một số 8 bit, thường tối đa hóa việc sử dụng một byte trong một số RDBMS.
    - Đặt ràng buộc NOT NULL nếu áp dụng để cải thiện hiệu suất tìm kiếm.
# Task queue - Message queue

## 1 Task Queue
Task queue nhận các task và các dữ liệu liên quan, xử lý và xuất ra kết quả. Ngoài ra nó có thể hỗ trợ lập kế hoạch và sử dụng để chạy các computationally-intensive jobs bên dưới.
* Celery hỗ trợ tốt cho việc lập kế hoạch và chủ yếu hỗ trợ python.

## 2 Message Queue
Message Queue nhận, giữ và gửi các message. Nếu các hành động được thực thi quá chậm, message queue có thể được sử dụng với quy trình như sau:
* Ứng dụng sẽ đưa một công việc vào queue và thông báo cho người dùng về trạng thái công việc
* Một công việc sẽ được chọn từ queue, xử lý nó và thông báo kết quả.

Người dùng sẽ không bị khóa và các công việc sẽ được xử lý ngầm bên dưới. Trong thời gian chờ đợi, người dùng có thể tùy ý làm một số lượng nhỏ các xử lý để xem như công việc đã hoàn thành. Ví dụ: Khi bạn post một facebook, facebook có thể hiện ngay trên dòng thời gian của bạn, nhưng sẽ mất một chút thời gian để có thể hiện lên dòng thời gian của những người khác.
* Redis là một ví dụ hữu ích về simple message broker nhưng mesage có thể bị mất
* RabbitMQ là một message broker được sử dụng phổ biến nhưng yêu cầu người sử dụng phải thích ứng với giao tiếp "AMQP" và quản lý các node của mình.
* Amazon SQS được sử dụng để lưu trữ message, nhưng có thể có độ trễ cao và có khả năng message được gửi 2 lần.

## 3 So sánh TaskQueue và Message Queue

Message Queue | Task Queue
--- | ---  
Là chức năng cơ bản của việc truyền, giữ và vận chuyển message trên một ứng dụng hoặc hệ thống dịch vụ | Quản lý công việc được thực hiện và được xem như một message queue. Trong task queue có thể có các task dependency với nhau

### DNS (Domain name system)
- Chuyển tên miền thành địa chỉ IP
- Khi User nhập vào tên miền thì DNS server sẽ chịu trách nhiệm chuyển tên miền thành địa chỉ IP và truy cập vào web có địa chỉ IP tương ứng. Vì vậy User chỉ cần nhớ tên miền, không cần thiết phải nhớ địa chỉ IP của web đó.

### CDN (Content delivery network)
- Là mạng phân phối các proxy server trên toàn cầu, cung cấp nội dung từ các vị trí gần hơn cho user. Các thông tin trên CDN chủ yếu là các static ví dụ như: html/css/js/image/video.
- CDN có thể cả thiện đáng kể hiệu suât theo 2 cách:
    - User sẽ nhận nội dung từ các trung tâm dữ liệu gần họ
    - Your server sẽ không cần request những thứ mà CDN cung cấp.
- Push CDN: Nhận nội dung mới bất cứ khi nào có sự thay đổi nội dung từ máy chủ.(Content sẽ được tải sẵn lên tất cả các CDN server ở tất cả vị trí, khi user yêu cầu chỉ cần lên lấy)
- Pull CDN: Lấy nội dung mới từ máy chủ khi user yêu cầu. Vì vậy, khi thực hiện request đầu tiên của user tốc độ truy cập không được cải thiện. Ví dụ: Bạn là người Mỹ và post 1 bài báo, các image/video sẽ được lưu trữ lên mấy chủ CDN bên Mỹ. Phần lớn đôc giả ở Viêt Nam muốn tải bài báo đó về, thì người đầu tiên request sẽ phải qua máy chủ CDN bên Mỹ và load các thông tin đó về máy chủ CDN bên Việt Nam, sau đó các request sau sẽ chỉ cần vào CND bên Việt Nam để lấy
    - Time-to-live (TTL) xác định khoảng thời gian lưu trữ nội dung. Pull CDNs giảm thiểu dung lượng lưu trữ trên CDN, nhưng có thể tạo ra lưu lượng dư thừa nếu các dữ liệu hết hạn và được pull trước khi chúng thay đổi.

### Bất lợi của CDN
- Chi phí cao.
- Nội dung có thể cũ nếu nó được cập nhật trước khi TTL hết hạn.
- CDNs yêu cầu thay đổi URL của nội dung static để trỏ tới CDN.

### Race conditions (Tranh chấp)
Khi hai process A và B cùng write 1 giá trị. A write với giá trị 1, đến lượt B thực hiện write với giá trị 2. Kết quả sẽ bị mất gía trị của A ghi lúc đầu