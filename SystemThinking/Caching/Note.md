# Cache
## Vai trò của cache
<div align="center">
    <img src="https://camo.githubusercontent.com/7acedde6aa7853baf2eb4a53f88e2595ebe43756/687474703a2f2f692e696d6775722e636f6d2f51367a32344c612e706e67" />
</div>

- Caching cải thiện thời gian tải trang và có thể giảm tải trên máy chủ và cơ sở dữ liệu của bạn.
- Cơ sở dữ liệu thường được hưởng lợi từ việc phân phối đồng đều các lần đọc và ghi trên các phân vùng của nó. Các mục phổ biến có thể làm lệch phân phối, gây tắc nghẽn. Đặt bộ nhớ cache ở phía trước cơ sở dữ liệu có thể giúp hấp thụ tải không đồng đều và tăng đột biến về lưu lượng truy cập.

### Các loại caching
    - Client caching
    - CDN caching
    - Web server caching
    - Database caching
    - Application caching

## Các thuật toán apply cho cache

### Least recently used (LRU)
Thay thế những phần tử được gọi xa nhất tính tới thời điểm hiện tại.

<div align="center">
    <img src="https://upload.wikimedia.org/wikipedia/commons/8/88/Lruexample.png" />
</div>

### Least-frequently used (LFU)
Thay thế những phần tử có số lần gọi đến ít nhất.
<div align="center">
    <img src="https://xuri.me/wp-content/uploads/2016/08/lfu-algorithm.png" />
</div>


## Bài tập
- Config file "sudo nano /etc/nginx/sites-available/default" 
<div align="center">
    <img src="img/conf.png" />
</div>

- Run nginx: "sudo systemctl restart nginx"
- Kiểm tra: curl -I http://localhost/toan.jpg
<div align="center">
    <img src="img/cache.png" />
</div>

### Tài liệu tham khảo
- [Cache](https://en.wikipedia.org/wiki/Cache_replacement_policies#Least_Recently_Used)
