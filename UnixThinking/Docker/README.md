# Docker

## 1. Docker là gì?

Docker là một open platform cung cấp cho người sử dụng những công cụ và service để người sử dụng có thể đóng gói và chạy chương trình của mình trên các môi trường khác nhau một cách nhanh nhất.

## 2. Các thành phần cơ bản của docker

### 2.1. Image

Image Là file ảnh, file nền của một hệ điều hành, một nền tảng, một ngôn ngữ (vd: ubuntu image, ruby image, rails image, mysql image…)

Từ các image này, bạn sẽ dùng nó để tạo ra các container.

### 2.2. Container 

Container Là một máy ảo, được cấu thành từ 1 image và được đắp thêm 1 lớp “trang trí” writable-file-layer. Các thay đổi trong máy ảo này (cài thêm phần mềm, tạo thêm file…) sẽ được lưu ở lớp trang trí này.

Các container này sẽ dùng chung tài nguyên của hệ thống (RAM, Disk, Network…), chính nhờ vậy, những container của bạn sẽ rất nhẹ, việc khởi động, kết nối, tương tác sẽ rất nhanh gọn.

### 2.3. Docker Engine  

Docker Engine quản lý việc bạn tạo image, chạy container, dùng image có sẵn hay tải image chưa có về, kết nối vào container, thêm, sửa, xóa image và container, ....

### 2.4 Docker Hub

Docker Hub Là 1 trang chia sẻ các image, nó như kiểu github hay bitbucket vậy.

## 3. Các lệnh cơ bản trong docker

- Pull một image từ Docker Hub
```
docker pull {image_name}
```


- Liệt kê các images hiện có
```
docker images
```

- Xóa một image
```
docker rmi {image_id/name}
```

- Liệt kê các container đang chạy

```
docker ps
docker ps -a #Liệt kê các container đã tắt
```

- Xóa một container
```
docker rm -f {container_id/name}
```

- Đổi tên một container
```
docker rename {old_container_name} {new_container_name}
```

- Khởi động một container 
```
docker start {new_container_name}
docker exec -it {new_container_name} /bin/bash
```

- Tạo mới một container, đồng thời khởi động với tùy chọn cổng và volume
```
docker run --name {container_name} -p {host_port}:{container_port} -v {/host_path}:{/container_path} -it {image_name} /bin/bash
```

- Xem các thay đổi trên container
```
docker diff {container_name}
```

- Commit các thay đổi trên container và image
```
docker commit -m "message" {container_name} {image_name}
```

- Save image thành file .tar
```
docker save {image_name} > {/host_path/new_image.tar}
```

- Tạo một image mới từ file .tar
```
cat musashi.tar | docker import - {new_image_name}:latest
```

- Xem lịch sử các commit trên image
```
docker history {image_name}
```

- Khôi phục lại images từ IMAGE_ID
```
docker tag {iamge_id} {image_new_name}:{tag}
```

- Build một image từ container
```
docker build -t {container_name} .
# Dấu . ở đây ám chỉ Dockerfile đang nằm trong thư mục hiện tại.
```

## 4. Docker file 

- React app

```
FROM node:8

# Override the base log level (info).
ENV NPM_CONFIG_LOGLEVEL warn

# Install and configure `serve`.
RUN npm install -g serve
EXPOSE 5000


# Install all dependencies of the current project.
COPY package.json package.json
COPY package-lock.json package-lock.json
RUN npm install

# Copy all local files into the image.
COPY src src
COPY public public

# Build for production.
RUN npm run build --production

# Run service
CMD serve -s build
``` 

- Java app

```
FROM openjdk:8-jdk-alpine

# Working directory for Tomcat
VOLUME /tmp

# Add the service itself
ARG JAR_FILE

# Copy jar file
ADD ${JAR_FILE} app.jar

# Expose port
EXPOSE <PORT_NUMBER>

# Command to run Application
ENTRYPOINT ["java", "-jar", "app.jar"]
```