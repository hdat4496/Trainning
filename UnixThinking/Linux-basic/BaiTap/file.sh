#!/bin/bash

_dir_file = $HOME/Linux-thinking/Linux-basic/BaiTap/
printf "\nList file > 100k: \n"
find $_dir_file  -type f -size +100k | xargs -n 1

printf "\nPlease choose a option:\n"
printf "1 - Delete\n"
printf "2 - Compress\n"


read option

if [ $option = 1 ]; then

    # Ghi log va xoa file
    echo "[Time: $(date)]\nList file remove: \n" >> $HOME/Linux-thinking/Linux-basic/BaiTap/logfile
    find $_dir_file -type f -size +100k >> $HOME/Linux-thinking/Linux-basic/BaiTap/logfile

    find $_dir_file -type f -size +100k | xargs -d"\n" rm
    printf "Deleted successful.\n"
elif [ $option = 2 ]; then

    find $_dir_file -type f -size +100k -print0 | sudo tar -czvf $HOME/Linux-thinking/Linux-basic/BaiTap/compress.tar.gz -T -
    printf "Compressed successful.\n"
else
    printf "Invalid selection.\n"
fi

