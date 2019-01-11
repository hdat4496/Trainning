# LINUX PROGRAMMING

## 1. Debuging

- Tạo file demo.cpp

- Biên dịch file mã nguồn với option -g để có thể debug bằng GDB

```
g++ -g demo.cpp -o demo
```

- Debug file thực thi $ gdb [file_name]

```
gdb demo
```

- Các lệnh thường được sử dụng trong GDB

```
(gdb) r     # Thực thi chương trình cho đến khi gặp lỗi hoặc breakpoint

(gdb) b     # Đặt breakpoint tại dòng hiện tại
(gdb) b 10  # Đặt breakpoint tại dòng 10
(gdb) b main  # Đặt breakpoint tại hàm main

(gdb) d 1   # Xóa breakpoint đầu tiên được tạo
(gdb) d 3   # Xóa breakpoint thứ 3 được tạo

(gdb) s     # Thực thi dòng lệnh tiếp theo, nhảy vào function nếu gọi

(gdb) n     # Thực thi dòng lệnh tiếp theo nhưng không nhảy vào function

(gdb) c     # Thực thi chương trình từ dòng hiện tại cho đến khi gặp lỗi hoặc breakpoint

(gdb) p var # Hiển thị giá trị hiện tại của biến var

(gdb) bt    # Hiển thị Stack Trace

(gdb) q     # Dừng debug, thoát khỏi GDB
```

## 2. Build System

### 2.1. Makefile là gì?
Makefile là một file dạng script chứa các thông tin:

- Cấu trúc project (file, sự phụ thuộc)

- Các lệnh để tạo file

Lệnh make sẽ đọc nội dung Makefile, hiểu kiến trúc của project và thực thi các lệnh

### 2.2. Tạo makefile đơn giản

- Tạo file hello.c

```c
#include <stdio.h>
 
int main() {
    printf("Hello, world!\n");
    return 0;
}


```

- makefile 

```
all: hello.exe

hello.exe: hello.o
	 gcc -o hello.exe hello.o

hello.o: hello.c
	 gcc -c hello.c
     
test: 
	./hello.exe
clean:
	 rm hello.o hello.exe
```

## 3. Memory Management

### Pages and Paging

- Bộ nhớ bao gồm các bit tạo thành byte. Byte sẽ compose ra các words, từ đó các words compose ra các pages. Page là địa chỉ nhỏ nhất của bộ nhớ mà bộ quản lý bộ nhớ (MMU) có thể quản lý. Do đó, không gian địa chỉ ảo được ghi trên các Page. Kích thước các page được quy định bởi kiến trúc máy tính. Thông thường là 4KB cho hệ thống 32 bit và 8KB cho hệ thống 64 bit.
- Paging là quá trình di chuyển dữ liệu từ bộ nhớ vật lý sang lưu trữ thứ cấp. 
- Sharing and copy-on-write: dữ liệu được chia sẻ có thể được đọc và ghi. Tuy nhiên để giải quyết vấn đề đồng bộ, MMU có thể sử dụng phương pháp copy-on-write để tạo ra bản sao phục vụ cho các thao tác ghi.

### Memory Region

Kernel phân chia các Page thành những khối có những thuộc tính riêng. Các khối này gọi là Memory Region (vùng nhớ). Có các loại vùng nhớ phổ biến sau đây:

- Text Segment: chứa đoạn mã của các tiến trình, các chuỗi ký tự, hằng số và các dữ liệu read-only (chỉ đọc).

- Stack: chứa các hàm thực thi của tiến trình, các biến cục bộ của các hàm đó và giá trị trả về. Trong các tiến trình đa luồng, mỗi Thread có một Stack riêng.

- Heap: chứa các dữ liệu được cấp phát động. Vùng nhớ này có thể ghi. Các hàm như malloc() có thể yêu cầu cấp phát từ vùng nhớ này.

- BSS Segment: chứa các biến toàn cục chưa được khởi tạo.

### Allocating Dynamic Memory
Dynamic Memory được cấp phát khi chương trình chạy, không phải lúc biên dịch. Kích thước và tuổi thọ vùng nhớ này được quyết định bởi người lập trình.

Phương thức cơ bản nhất trong C để cấp phát vùng nhớ động là malloc():

```c
#include <stdlib.h>
void* malloc(size_t size);
```

Nếu yêu cầu cấp phát thành công, hàm sẽ trả về con trỏ đến địa chỉ vùng nhớ mới được cấp phát với giá trị chưa được xác định. Nếu thất bại, hàm trả về NULL.

Ví dụ yêu cầu cấp phát vùng nhớ độ lớn 1KB:

```c
char* p;
p = malloc(1024);
if(!p)
    perror("malloc fail");
```


### Allocating Arrays

```c
    #include <stdlib.h>
    void * calloc (size_t nr, size_t size);
```

ví dụ

```c
y = calloc (50, sizeof (int));
if (!y)
{
    perror ("calloc fail");
}
```

### Resizing Allocations
Ngôn ngữ C cung cấp cơ chế để điều chỉnh kích thước của một vùng nhớ động đã được cấp phát 

```c
    #include <stdlib.h>
    void * realloc (void *ptr, size_t size);
```

ví dụ:

```c
struct map *p;
p = calloc (2, sizeof (struct map));
if (!p)
{
    perror ("calloc fail");
}
struct map *r;
r = realloc (p, sizeof (struct map));
if (!r)
{
    perror ("realloc fail");
}
```

### Freeing Dynamic Memory

Bộ nhớ được phân bổ với malloc (), calloc () hoặc realloc () phải được trả lại cho hệ thống khi không còn được sử dụng thông qua free ()

```c
#include <stdlib.h>
void free(void* ptr);
```

## 4. Networking

### Socket

Socket là một cổng logic mà một chương trình sử dụng để kết nối với một chương trình khác chạy trên một máy tính khác trên Internet. Chương trình mạng có thể sử dụng nhiều Socket cùng một lúc, nhờ đó nhiều chương trình có thể sử dụng Internet cùng một lúc.

Có 2 loại Socket:

- Stream Socket: Dựa trên giao thức TCP (Tranmission Control Protocol) việc truyền dữ liệu chỉ thực hiện giữa 2 quá trình đã thiết lập kết nối. Giao thức này đảm bảo dữ liệu được truyền đến nơi nhận một cách đáng tin cậy, đúng thứ tự nhờ vào cơ chế quản lý luồng lưu thông trên mạng và cơ chế chống tắc nghẽn.

- Datagram Socket: Dựa trên giao thức UDP (User Datagram Protocol) việc truyền dữ liệu không yêu cầu có sự thiết lập kết nối giữa 2 quá trình. Ngược lại với giao thức TCP thì dữ liệu được truyền theo giao thức UDP không được tin cậy, có thế không đúng trình tự và lặp lại. Tuy nhiên vì nó không yêu cầu thiết lập kết nối không phải có những cơ chế phức tạp nên tốc độ nhanh, ứng dụng cho các ứng dụng truyền dữ liệu nhanh như chat, game, stream ...


### Blocking và Non-blocking Socket

- Mặc định, TCP socket là một Blocking socket. Blocking có nghĩa rằng một tiến trình chỉ có thể chờ một thao tác được thực hiện mà không làm được gì khác trong thời gian đó. Chẳng hạn như khi gọi hàm connect(), chương trình sẽ bị block cho đến khi kết nối được thực hiện hoặc lỗi xảy ra. Hoặc khi gọi recv() để đọc dữ liệu từ stream, chương trình cũng sẽ bị block cho đến khi nhận được ít nhất một byte dữ liệu. 

- Ở chế độ Non-blocking, chương trình không cần phải đợi một thao tác được hoàn thành để đảm bảo chương trình được chạy xuyên suốt. Ví dụ, khi gọi hàm recv() ở chế độ Non-blocking, nó sẽ nhận dữ liệu bất cứ khi nào có dữ liệu tồn tại trong socket, trong khi chương trình vẫn có thể thực hiện những thao tác khác. Để chuyển sang chế độ Non-blocking, ta gọi hàm select().

### Network Byte Order

Cách mà cho phép các máy tính khác nhau về cách tổ chức dữ liệu (thứ tự sắp xếp các byte) có thể giao tiếp được với nhau. Trong giao thức kết nối mạng (internet protocals) quy định một cách thức sắp xếp byte chuẩn cho dữ liệu truyền qua mạng.

Network Byte Ordering quy định kiểu định dạng dữ liệu gửi/nhận qua network là Big Endian hay Little Endian, chẳng hạn chuẩn của Intel theo Litlle Endian, còn Sparc là Big Endian khi đó hai bên gửi/nhận phải thống nhất với nhau kiểu định dạng này.

Có 2 dạng endian: big endian và little endian.

- big endian: byte có giá trị lớn sẽ ghi  trước, byte có giá trị nhỏ ghi sau. Cách ghi này có lợi điểm là giống suy nghĩ của con người là đọc từ trái qua phải. Ví dụ: số ba trăm hai mươi mốt sẽ được ghi là 321.

- little endian: byte có giá trị nhỏ sẽ được ghi trước, byte có giá trị lớn ghi sau. Cách ghi này có lợi cho tính toán của máy, ví dụ muốn biết đó là số chẵn hay lẻ chỉ cần đọc byte đầu tiên. Khi đó, số ba trăm hai mươi mốt sẽ được ghi là 123.

## Multi-threading

### POSIX Thread

Thư viện POSIX thread là một chuẩn cho lập trình thread trong C/C++. Nó cho phép tạo ra các ứng dụng chạy song song theo luồng, rất hiệu quả trên hệ thống nhiều bộ vi xử lý hoặc bộ vi xử lý nhiều nhân ở đó các luồng xử lý có thể được lập lịch chạy trên các bộ xử lý khác nhau do đó tăng được tốc độ xử lý song song hoặc xử lý phân tán.

Dựa theo chức năng mà các API của pthread được chia làm 4 loại

- Thread management: Là các hàm sử dụng để tạo, hủy, detached, join thread cũng như set/get các thuộc tính của thread nữa.
- Mutexes: Là các hàm sử dụng để tạo, hủy, unlocking, locking mutex (“mutual exclusion” : vùng tranh chấp), cũng như set/get các thuộc tính của mutex.
- Condition variables: Là các hàm để tạo, hủy, đợi hoặc phát tín hiệu dựa trên giá trị của một biến cụ thể.
- Synchronization: Là các hàm dùng để quản lý việc read/write lock và barriers. => Những hàm thuộc cùng 1 loại API ở trên sẽ có tên tương tự nhau. Ví dụ: pthread_create_xXX, pthread_join; pthread_mutex_XXX, pthread_cond_XXX etc

Tạo thread
```c
 int pthread_create(pthread_t * thread, pthread_attr_t * attr, void * (*start_routine)(void *), void * arg);
 ```
 - Chức năng của hàm pthread_create tạo mới một thread. Tham số đầu vào là một biến của thread thuộc kiểu pthread_t, một thuộc tính của thead thuộc kiểu pthread_attr_t, đặt là NULL nếu giữ thuộc tính thread mặc định, một thủ tục kiểu void và một con trỏ đối số cho hàm thuộc kiểu void.
- Thread sẽ đựoc chạy ngay sau khi kết thúc hàm tạo.

Đồng bộ Thread cung cấp 3 cơ chế đồng bộ:

- ***mutexes*** – Khoá loại trừ lẫn nhau: Khoá việc truy cập các biến của các thead khác. Do đó thực thi này có toàn quyền truy cập và thiết lập cho các biến.
    - pthread_mutex_lock() – giữ  khóa trên biến mutex chỉ định. Nếu mutex đã bị khoá bởi một thread khác, lời gọi này sẽ bị giữ lại cho tới khi mutex này mở khoá.
    - pthread_mutex_unlock() – mở khoá biến mutex. Một lỗi trả về nếu mutex đã mở khoá hoặc được sở hữu của một thread khác.
    - pthread_mutex_trylock() – thử khoá một mutex hoặc sẽ trả về mã lỗi nếu bận. Hữu ích cho việc ngăn ngừa trường hợp khoá chết (deadlock).
  
- ***joins*** – Thiết lập một thread đợi cho đến khi những thread khác hoàn thành.
    - pthread_join() được sử dụng để đợi cho việc kết thúc của một thread hoặc chờ để tái kết hợp với luồng chính của hàm main.

- ***condition variable*** - biến kiểu pthread_cond_t và được sử dụng với các hàm thích hợp để một thread đợi và sau đó lại tiếp tục xử lý. Kỹ thuật này cho phép các thread dừng thực thi và huỷ bỏ xử lý cho đến khi một vài điều kiện là true. Biến điều kiện luôn luôn phải kết hợp với một mutex. Thread sẽ chờ mãi mãi nếu tín hiệu không được gửi. Bất kỳ mutex nào cũng có thể được sử dụng, không có liên kết tường minh giữa mutex và biến điều kiện.
    - Khởi tạo/ hủy:
        - pthread_cond_init()
        - pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
        - pthread_cond_destroy()
    - Đợi dựa trên điều kiện:
        - pthread_cond_wait() – khoá mutex và đợi tính hiệu của biến điều kiện
        - pthread_cond_timedwait() – đặt giới hạn về thời gian bao lâu thì sẽ khoá
    - Đánh thức thread dựa trên điều kiện:
        - pthread_cond_signal() – khởi động một thread đang đợi bởi biến điều kiện.
        - pthread_cond_broadcast() – đánh tức tất cả các thread được khoá bởi biến điều kiện
  
### STD Thread

- Trong C++ được xây dưng header thread.h để tạo ra các multithreaded C++ programs. #include <thread>

- Tạo thread: Tạo 1 thread object: thread t

- Khởi tạo thread: 

    - Có thể khởi tạo bằng function: thread t(<functionName>);

    - Có thể khởi tạo bằng object: thread t(<objectName>);



- Join threads: Thread class cung cấp method join(), hàm này chỉ return khi tất cả các thread kết thúc.
- Joinable threads: 1 joinable thread là 1 thread mà đại diện cho 1 execution mà chưa join. Kiểm tra bằng hàm bool joinable()

- not joinable threads: khi 1 thread được khởi tạo mặc định hoặc được moved/assigned tới 1 thread khác hoặc join, detach hàm đã được gọi
- Detaching thread: Hàm này tách 1 thread từ 1 thread cha, nó cho phép thread cha và thread con được chạy ngay lập tức từ cái còn lại. Sau khi call detach functon, các thread sẽ không đồng bộ trong bất kỳ cách nào. Thread trở thành "not joinable" sau khi gọi detach()

- ThreadID: Mọi thread đều có 1 unique identifier. Và trong class thread có 1 public function cho phép get ra giá trị của thread Id. id get_id()

- Mutex: Class mutex là 1 đồng bộ hoá nguyên thuỷ được sử dụng để bảo vệ được chia sẻ từ các simultaneous access (truy cập cùng lúc). 1 mutex có thể được khoá và mở. Nếu 1 mutex được khoá, thread hiện tại chứa mutex đó sẽ hoạt động cho đến khi nó mở khóa trở laị. Nghĩa là không 1 thread nào khác có thể thực hiện bất kỳ instructions từ các khối code được bao quanh bởi mutex cho đến khi thread hiện tại mở nó. Sử dụng mutex bằng cách include mutex header vào chương trình #include <mutex>. Sau đó tạo 1 biến global kiểu mutex. Biến này được sử dụng để dồng bộ việc truy cập vào dữ liệu.


### std::mutex

std::mutex được dùng để bảo vệ tài nguyên, tránh việc nhiều thread đồng thời truy cập làm thay đổi tài nguyên khi mà có thread khác đang cần dùng tới.

```c
struct Counter {
    int value;
    Counter() : value(0) {}

    void increment(){
        ++value;
    }
    void decrement(){
        if(value == 0){
            throw "Value cannot be less than 0";
        }
        --value;
    }
};

struct ConcurrentCounter {
    std::mutex mutex;
    Counter counter;

    void increment(){
        mutex.lock();
        counter.increment();
        mutex.unlock();
    }

    void decrement(){
        mutex.lock();
        counter.decrement();        
        mutex.unlock();
    }
};
```

### std::recursive_mutex

C++ có 1 đặc điểm chết người đó là không được lock mutex 2 lần liên tiếp khi mà chưa unlock trong cùng 1 thread . Giai pháp khi cần phải lock 2 lần đó là dùng std::recursive_mutex

```c
struct Complex {
    std::recursive_mutex mutex;
    int i;

    Complex() : i(0) {}

    void mul(int x){
        std::lock_guard<std::recursive_mutex> lock(mutex);
        i *= x;
    }

    void div(int x){
        std::lock_guard<std::recursive_mutex> lock(mutex);
        i /= x;
    }

    void both(int x, int y){
        std::lock_guard<std::recursive_mutex> lock(mutex);
        mul(x);
        div(y);
    }
};
```

### std::lock_guard

std::lock_guard cho phép bạn dùng mutex trong một đoạn code, thay vì phải tự lock và unlock ( đôi khi quên unlock là chương trình đơ luôn) chỉ cần gọi std::lock_guard.

```c
struct ConcurrentCounter {
    std::mutex mutex;
    Counter counter;

    void increment(){
       std::lock_guard<std::mutex> lk(mutex);
        counter.increment();
    }

    void decrement(){
        std::lock_guard<std::mutex> lk(mutex);
        counter.decrement();        
    }
};
```

### std::condition_variable
thread add thêm thành phần vào queue có thể dùng std::condition_variable::notify_one để báo cho các thread đang chờ rằng queue khác rỗng. Trong nhiều trường hợp cần gửi thông báo tới nhiều threads thì dùng notify_all

```c
std::mutex mutex;
std::condition_variable notEmpty;

void addQueueWorker()
{
    std::unique_lock<std::mutex> lk(mutex); 
    queue1.push(e)
    lk.unlock(); // dung unique_lock để gọi unlock trước khi kết thúc đoạn code thay vì lock_guard
    notEmpty.notify_one(); 
}

void worker1()
{

    std::lock_guard<std::mutex> lk(mutex);
    if ( queue.size() == 0)
   {
        // Chờ ở đây vì queue đang rỗng, việc chờ không tốn CPU time, khi nào có notify từ threads khác 
        // thì sẽ chạy đoạn code đằng sau
        notEmpty.wait(lk);
   }
    auto e = queue.front();
    queue.pop();
    do_some_thing(e);   
}
```

std::condition_variable là cách hay để gửi thông báo giữa các thread mà không tốn thời gian CPU

### std::atomic

std::atomic được gọi là lock-free . Nghĩa là khi dùng atomic thì khỏi cần dùng lock để chia sẻ tài nguyên giữa các thread. Đáng tiếc là atomic cũng chỉ hỗ trợ một số type nhất định ( có thể check bằng atomic::is_lock_free() , và không đảm bảo hỗ trợ giống nhau ở các platform khác nhau, tốt nhất nên check. 

```c
#include <atomic>

struct AtomicCounter {
    std::atomic<int> value;

    void increment(){
        ++value;
    }

    void decrement(){
        --value;
    }

    int get(){
        return value.load();
    }
};
```

## Multi-Processing

### Semaphore

- là một variable hoặc abstract data type được sử dụng để kiểm soát việc truy cập đông thời vào một tài nguyên chung của hệ thống đa tiến trình. Semaphore hỗ trợ 2 hoạt động chính: 
    - wait: giảm và khóa cho đến khi semaphore được mở
    - signal: tăng và cho phép thêm 1 tiến trình mới được tham gia hoạt động 
- Các loại Semaphore
    - Mutex Semaphore: Cho phép điều khiển hoạt động truy nhập đơn lẻ vào tài nguyên chia sẻ của hệ thống. Đảm bảo loại trừ xung đột trong hoạt động truy nhập đồng thời của nhiều tác vụ, chỉ có 1 tác vụ được thực thi tại mỗi thời điểm
    - Counting Semaphore: Điều khiển tài nguyên mà có thể phục vụ cùng lúc nhiều tác vụ hoặc một nguồn tài nguyên cho phép phục vụ một số lượng nhất định các tác vụ không đồng bộ và hoạt động đồng thời. Số lượng luồng được quyết dịnh bởi biến đếm N của Semaphore. Thực chất mutex semaphore là một dạng đặc biệt của counting semaphore với biến đếm N=1.
- Nhược điểm: 
    - Chủ yếu sử dụng các biến toàn cục trong việc điều khiển hoạt động đồng bộ nên có thể truy nhập bất kỳ đâu trong hệ thống => khó kiểm soát
    - Không có sự liên kết giữa semaphore và dữ liệu mà nó điều khiển

```
    P(Semaphore s)
    {
    chờ cho đến khi s > 0, rồi s:= s-1;
    /* phải là toán tử nguyên tố (không bị chia cắt) một khi phát hiện được s > 0 */
    }

    V(Semaphore s)
    {
    s:= s+1;   /* phải là toán tử nguyên tố */
    }

    Init(Semaphore s, Integer v)
    {
     s:= v;
    }
```

***Sự khác nhau giữa SEMAPHORES và MUTEXES***

- Semaphore hỗ trợ multi process. Như trên ví dụ 1: với mutex thằng nào(task/process) lock tủ thì chính thằng đó phải ra unlock tủ. Thằng semaphore cho phép thằng khác unlock cái tủ đó nếu cần thiết.
- Mutex hỗ trợ cái priority inversion (nôm na là mỗi khi gọi hàm os_wait_mutex (đợi chìa khóa) - hàm này làm tăng độ ưu tiên của task chứa nó, độ ưu tiên của task này sẽ bằng độ ưu tiên của task đang chạy, giúp tránh tình trạng deadlock của hệ thống). Xem thêm
- Khi một task đang giữ mutex. Hệ điều hành cho phép xóa cái mutex đó khi cần thiết. Semaphore không hỗ trợ cơ chế này.

### Shared Memory

- Là cơ chế  hai hay nhiều process được cấp một không gian bộ nhớ chung, vì thế chúng có thể trao đổi thông tin qua vùng nhớ chung này.

<img src="https://1.bp.blogspot.com/-DzAWwz1zQys/VXHUz-SumNI/AAAAAAAAXzw/WSiH-Z2wc8I/s1600/shared-memory.jpg"/>


- Việc tương tác giữa các process sẽ hoàn toàn do việc đọc/ghi trên vùng nhớ chung này. OS không hề can thiệp vào quá trình này, thế nên shared memory là phương pháp nhanh nhất (nhanh về mặt tốc độ) để các process nói chuyện với nhau. Tuy nhiên, nhược điểm của phương pháp này là các process phải tự quản lý việc đọc ghi dữ liệu, và quản lý việc tranh chấp tài nguyên.
- Vấn đề sẽ xảy ra nếu cả 2 processes cùng ghi vào vùng nhớ chung. Dữ liêu ghi sau sẽ đè lên dữ liệu ghi trước, và có thể dẫn đến sai lệch dữ liệu. Ngoài ra, khi sử dụng shared memory cũng cần tránh việc trỏ tới dữ liệu nằm ngoài vùng nhớ chung, vì không gian nhớ của các process là độc lập với nhau.

### Message Queue

- Message Queue nhận, giữ và gửi các message. Nếu các hành động được thực thi quá chậm, message queue có thể được sử dụng với quy trình như sau:

    - Ứng dụng sẽ đưa một công việc vào queue và thông báo cho người dùng về trạng thái công việc
    - Một công việc sẽ được chọn từ queue, xử lý nó và thông báo kết quả.

- Người dùng sẽ không bị khóa và các công việc sẽ được xử lý ngầm bên dưới. Trong thời gian chờ đợi, người dùng có thể tùy ý làm một số lượng nhỏ các xử lý để xem như công việc đã hoàn thành. Ví dụ: Khi bạn post một tweet, tweet có thể hiện ngay trên dòng thời gian của bạn, nhưng sẽ mất một chút thời gian để có thể hiện lên dòng thời gian của những người khác.
- Redis là một ví dụ hữu ích về simple message broker nhưng mesage có thể bị mất
- RabbitMQ là một message broker được sử dụng phổ biến nhưng yêu cầu người sử dụng phải thích ứng với giao tiếp "AMQP" và quản lý các node của mình

- Amazon SQS được sử dụng để lưu trữ message, nhưng có thể có độ trễ cao và có khả năng message được gửi 2 lần

<img src="https://docs.wso2.com/download/attachments/48287639/MessageQueue.jpg?version=1&modificationDate=1449564526000&api=v2"/>


### Pipes
- là cơ chế theo dạng đường ống kết nối luồng dữ liệu từ quy trình naỳ đến quy trình khác. Cho phép dữ liệu đầu ra của process này sẽ được chuyển đến đầu vào của process kia


<img src="https://4.bp.blogspot.com/-uj7fGL6lmr4/VXHUzn8P10I/AAAAAAAAXzs/DXOlJjSi9RI/s1600/pipe.png"/>

### Deamon process

- Daemon process là các tiến trình mà sẽ sống dai dẳng, hay nói đơn giản hơn là không chết sau khi làm một task nào đó mà sẽ đợi để làm một cái gì đó tiếp theo. Cacd daemon process thường gặp như là web server hay là database.
- Đặc điểm khi làm việc với daemon process: 

    - có thể sử dụng lệnh ps -axj để xem các Deamon process
    - Gọi hàm umask để set file mode creation cho một giá trị đã biết, thường là 0. Việc này để tránh việc process con kế thừa lại file mode từ process cha dẫn đến các hành vi không mong muốn.
    - Gọi fork và để cho process cha exit(). Việc này sẽ tạo ra một vài hiệu quả: đầu tiên là process cha exit() sẽ làm cho shell nghĩ là process đã kết thúc. Ngoài ra process con sẽ kế thừa group id từ process cha và có process id tách biệt, do đó chúng ta có thể đảm bảo là process con không phải là group leader, mà việc này sẽ là điều kiện tiên quyết cho việc gọi setsid ở dưới đây.
    - Gọi setsid để tạo session mới. Ở một số hệ thống sử dụng System V, chúng ta hay được recommend sử dụng kĩ thuật double fork. Việc này để đảm bảo process con không phải là session leader, tránh được việc nó sẽ dành quyền kiểm soát controlling terminal.
    - Chuyển directory hiện tại sang root directory. Việc này nhằm tránh khi process con chạy trong thư mục được mount, khiến cho thư mục này không thể bị unmount.
    - Đóng file descriptor ngay khi có thể
    - Một vài daemon mở file descriptor 0,1,2 vào /dev/null.

### Signal handling

Signal là một tín hiệu ngắt chương trình, cung cấp cơ chế để xử lý các sự kiện bất đồng bộ. Các sự kiện này có thể đến từ ngoài hệ thống phần mềm (người dùng chủ động kết thúc chương trình bằng Ctrl+C) hoặc đến từ bên trong chương trình (lỗi do thực hiện phép chia với 0). Một tiến trình cũng có thể gửi Signal đến tiến trình khác.

Các hàm xử lý Signal được đăng ký với nhân hệ điều hành có thể được thực thi bất cứ khi nào Signal được gửi đến trong suốt quá trình chạy chương trình.

- Một số giá trị Signal thông dụng

    Signal|Giá trị|Nội dung
    --|--|--|
    SIGHUP|1|Hangup (gọi lại tiến trình)
    SIGINT|2|Interrupt (Ngắt từ bàn phím `Ctrl+C`)
    SIGQUIT|3|Kết thúc từ bàn phím
    SIGKILL|9|Hủy tiến trình
    SIGTERM|15|kết thúc tiến trình
    SIGALRM|14| Sinh ra khi hết thời gian thiết lập
## IPC

IPC (Inter-Process Communication) là cơ chế hệ điều hành cung cấp để các tiến trình có thể giao tiếp với nhau.

