# Probabilistic data structure
## 1. Khái niệm
Khi xử lý các tập dữ liệu lớn, chúng ta thường muốn thực hiện một số kiểm tra đơn giản, chẳng hạn như số lượng các sản phầm duy nhất, các sản phầm được mua thường xuyên nhất và liệu một sản phẩm có tồn tại trong tập dữ liệu hay không. Cách tiếp cận phổ biến là sử dụng một số loại cấu trúc dữ liệu xác định như HashSet hoặc Hashtable cho các mục đích như vậy. Nhưng khi tập dữ liệu chúng ta đang xử lý trở nên rất lớn, các cấu trúc dữ liệu đó đơn giản là không khả thi vì dữ liệu quá lớn để vừa với bộ nhớ. Nó trở nên khó khăn hơn cho các ứng dụng trực tuyến thường yêu cầu dữ liệu được xử lý trong một lần truyền và thực hiện các cập nhật gia tăng.

Cấu trúc dữ liệu xác suất là một nhóm cấu trúc dữ liệu cực kỳ hữu ích cho dữ liệu lớn và các ứng dụng truyền trực tuyến. Nói chung, các cấu trúc dữ liệu này sử dụng hàm băm để phân ngẫu nhiên và đại diện cho một tập hợp các mục. Va chạm được bỏ qua nhưng lỗi có thể được kiểm soát tốt dưới ngưỡng nhất định. So sánh với các cách tiếp cận không có lỗi, các thuật toán này sử dụng ít bộ nhớ hơn và có thời gian truy vấn liên tục. 

## 2. Tính chất
Cấu trúc này không cung cấp câu trả lời chính xác nhất trong mọi trường hợp , thay vào đó chúng cung cấp câu trả lời với biên độ lỗi nhất định. Biên độ đó có thể được thực hiện nhỏ như mong muốn, nhưng phải có sự cân bằng giữa các yêu cầu về không gian và tỷ lệ lỗi. Do đó, các lập trình viên nên chọn tỷ lệ lỗi phù hợp nhất với nhu cầu của họ mà không cần sử dụng quá nhiều không gian.

## 3. Các loại Probabilistic data structure

### 3.1 Bloom filter

Bloom filter là một cấu trúc dữ liệu xác suất được dùng để kiểm tra một phần tử nó nằm trong một tập hợp. Có thể có lỗi dương tính sai, nhưng không bao giờ có âm tính sai(Có nghĩa là: Khi kết quả là "Có" thì có thể sai, nhưng kết quả "Không" luôn luôn chính xác). Bloom filter chỉ cho phép chèn nhưng không cho phép xóa. Khi chèn càng nhiều thì xác suất sai càng cao.

**Cách hoạt động**

- Bloom filter là một mảng m bit, tất cả đều bằng 0. Giả sử có k hàm băm khác nhau, mỗi hàm ánh xạ từ không gian các phần tử tới m vị trí trong bảng với xác suất như nhau. Thông thường k là một hằng số và nhỏ hơn nhiều so với m.

- Để chèn một phần tử, áp dụng k hàm băm để tính ra mảng k vị trí và gán cho các bit này giá trị 1.

- Để kiểm tra một phần tử có nằm trong tập hợp hay không, áp dụng k hàm băm để tính ra k vị trí trong mảng và kiểm tra xem tất cả các bit đó có giá trị 1 hay không. Nếu có một bit nào đó bằng 0 thì phần tử cần kiểm tra chắc chắn không nằm trong mảng. Nếu tất cả chúng đều bằng 1 thì phần tử đó có thể nằm trong mảng.

### 3.2 Cuckoo filter

Cuckoo filter là một cấu trúc dữ liệu xác suất được dùng để kiểm tra một phần tử nó nằm trong một tập hợp giống như Bloom filter. Cuckoo filter cải thiện thiết kế của Bloom filter bằng cách cho phép xóa, đếm có giới hạn và xác suất biên dương sai (a bounded false positive probability) trong khi vẫn duy trì được độ phức tạp không gian tương tự. Chúng sử dụng Cuckoo hashing để giải quyết đụng độ và trữ trong cuckoo hash table.

**Cách hoạt động**: 
Thay vì sử dụng bit đơn như bloom filter để lưu trữ dữ liệu, Cuckoo filter sử dụng một lượng nhỏ f-bit của key và 2 hash table b1, b2 cùng size.

Mỗi giá trị được hash bởi 2 hàm hash h1 và h2. Gía trị sẽ được gán vào 1 trong 2 hash table. Ta đặt giá trị x vào b1[h1(x)], nếu có một giá trị y nào đó nằm sẵn, y sẽ được loại bỏ và ta đặt y vào b2[h2(y)]. Nếu vị trí đó bị chiếm bởi một giá trị z nào khác, ta loại bỏ z và đặt z vào b1[h1(z)]. Tiếp tục thực hiện cho tới khi tìm được vị trí rỗng. Nếu tốn quá nhiều thời gian để tìm (có thể đặt maxLoop), ta kết luận không thể insert được. Lúc này ta sẽ hash lại mọi thứ với hàm hash mới hoặc tăng kích thước table.

Khi muốn xóa, chỉ việc hash giá trị với hàm h1 trên b1 và h2 trên b2, nếu giá trị ở đó tồn tại thì có thể xóa đi.

### 3.3 Count Min Sketch

Count-min Sketch là một cấu trúc dữ liệu xác suất phục vụ như một bảng tần số các sự kiện trong một luồng dữ liệu. Nó sử dụng các hàm băm để ánh xạ các sự kiện về tần số, nhưng khác với bảng băm chỉ sử dụng không gian tuyến tính phụ, với chi phí đếm quá tải một số sự kiện do đụng độ.

Count-min Sketch về cơ bản là cùng một cấu trúc dữ liệu với Bloom filter. Tuy nhiên, chúng được sử dụng khác nhau và do đó có kích thước khác nhau.

**Cách hoạt động**: Cấu trúc dữ liệu của 1 sketch gồm 1 mảng 2 chiều w cột và d dòng, mỗi dòng ứng với 1 hàm hash riêng biệt. Với mỗi dòng, khi một sự kiện đến sẽ dùng một hàm hash tương ứng để hash ra index của sự kiện đó trên mỗi dòng và tăng giá trị tại đó lên thêm 1. Kết quả ước lượng lấy giá trị nhỏ nhất trên mảng 2 chiều này.

### 3.4 HyperLogLog

HyperLogLog là một cấu trúc dữ liệu xác suất được sử dụng để đếm gần đúng các phần tử duy nhất trong một tập quá lớn (> 10^9) các phần tử mà các phương pháp thông thường sẽ tốn nhiều chi phí về bộ nhớ cũng như thời gian.

**Cách hoạt động**: HyperLogLog được mô tả bởi 2 tham số: số bit định nghĩa bucket p và một hàm hash h. Vậy bucket m sẽ có 2^p phần tử.

Băm mỗi phần tử trong tập, với mỗi phần tử đã băm: lấy p bit đầu tiên chỉ index trong bucket m. Số bit còn lại dùng để tính giá trị bằng cách đếm số lượng số 0 liên tiếp đầu tiên cộng 1 và lưu vào vị trí index trên. Nếu vị trí index trên đã có sẵn giá trị thì giá trị nào lớn hơn sẽ được lưu.

Ước lượng số lượng phần tử duy nhất bằng công thức:

```
DV = 0.66 * (2^p)^2 / [sum(from 1 to 2^p): 2^(-m[i])]
```


# Trie

## 1. Khái niệm
Trie (cây tiền tố) là một cấu trúc dữ liệu sử dụng cây có thứ tự, dùng để lưu trữ một mảng liên kết của các xâu ký tự. Không như cây nhị phân tìm kiếm, mỗi nút trong cây không liên kết với một khóa trong mảng. Thay vào đó, mỗi nút liên kết với một xâu ký tự sao cho các xâu ký tự của tất cả các nút con của một nút đều có chung một tiền tố, chính là xâu ký tự của nút đó. Nút gốc tương ứng với xâu ký tự rỗng.

## 2. Công dụng
* Auto Complete: thường được dùng trong mobile app và text editor do có khả năng tìm kiếm rất nhanh.
* Spell Checkers: gồm 3 bước: kiểm tra một từ có tồn tại trong từ điển, tạo ra đề xuất phù hợp, sắp xếp từ phù hợp nhất lên top. Trie có thể được sử dụng để lưu trữ từ điển đó và bằng cách tìm kiếm từ trên cấu trúc dữ liệu, có thể dễ dàng thực hiện kiểm tra chính tả một cách hiệu quả nhất. Sử dụng Trie không chỉ tra cứu một từ vào từ điển trở nên dễ dàng nhưng một thuật toán để cung cấp danh sách các từ hoặc gợi ý hợp lệ có thể dễ dàng được xây dựng.
* Automatic Command completion: thường được sử dụng trong Unix, các command ở /usr/bin/ps* có prefix mà ps có thể dùng để gợi ý kết quả.
* Network browser history: Trình duyệt giữ một lịch sử của các URL của các trang web mà bạn đã truy cập. Bằng cách tổ chức lịch sử này như là một trie, người dùng chỉ cần gõ tiền tố của một URL được sử dụng trước đó và trình duyệt có thể hoàn thành URL.


# Clean code (Chapter 3 - Functions)
- Tránh để lập code, nên tách những việc lập; đi lập lại nhiều lần thành 1 function thực hiện 1 chắc năng riêng biệt  để dễ quản lý hơn.
- Các functions phải small, small nhất có thể. Chia ra từng chức năng cơ bản nhất.
- 1 function chỉ nên làm 1 chức năng.
- Các câu lệnh if, else, while không nên lồng ghép nhau quá nhiều, tối đa không được lớn hơn 1 hoặc 2 lần.
- Reading code from Top to Bottom.
- Nên đặt tên cho function mô tả rõ chức năng, nhiệm vụ của nó.
- Number of arguments cho 1 function nên 0, 1 hoặc 2, tránh sử dụng 3 hoặc nhiều hơn.
- Tránh truyền các đối số kiểu boolean vào function
- Function càng ít đối số càng dễ hiểu.
- Khi 1 function có nhiều hơn 2 đối số, nên chuyển các đối số đó thành 1 object. 
- Tránh sử dụng tham số đầu ra.
- Function thường làm 1 chức năng gì đó hoặc trả lời 1 vấn đề gì đó. Không nên sử dụng cả 2 cùng 1 lúc.
- Ưu tiên dùng Exceptions để return lỗi.
- Mọi function hoặc mỗi block trong function nên có 1 entry và exit, không nên sử dụng beark, continue trong các vòng lập. Và never, ever, any goto statements

### Một số mã lỗi thường gặp trong java:
```java
    public enum Error {
        OK,
        INVALID,
        NO_SUCH,
        LOCKED,
        OUT_OF_RESOURCES,
        WAITING_FOR_EVENT;
    }
```


# Tài liệu tham khảo:
* [Introduction to Probabilistic Data Structures](https://dzone.com/articles/introduction-probabilistic-0)

* [Probabilistic Data Structures to Improve System Performance](https://workwiththebest.intraway.com/white-paper/probabilistic-data-structures-to-improve-system-performance/)

* [Bloom filter](https://vi.wikipedia.org/wiki/B%E1%BB%99_l%E1%BB%8Dc_Bloom)

* [Cuckoo Filter](https://brilliant.org/wiki/cuckoo-filter/#cuckoo-hashing)

* [Count–min sketch](https://en.wikipedia.org/wiki/Count%E2%80%93min_sketch)

* [Probabilistic data structures. Part 2. Cardinality](https://www.slideshare.net/gakhov/probabilistic-data-structures-part-2-cardinality)

* [Trie](https://vi.wikipedia.org/wiki/Trie)

* [Applications of Trie data structure](http://blog.xebia.in/index.php/2015/09/28/applications-of-trie-data-structure/)

* [Trie in Java](https://community.oracle.com/thread/2070706)