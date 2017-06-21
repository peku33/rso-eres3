FROM debian:jessie
RUN echo "deb http://ftp.debian.org/debian jessie-backports main" > /etc/apt/sources.list.d/10-jessie-backports.list
RUN apt update -y && apt install gradle -y
CMD [ "/bin/bash" ]
