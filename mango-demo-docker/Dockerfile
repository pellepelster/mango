FROM     ubuntu:14.04
MAINTAINER Christian Pelster <pelle@pelle.io>

# make sure the package repository is up to date
RUN apt-get update

RUN apt-get install -y openssh-server
RUN apt-get install -y firefox
RUN mkdir /var/run/sshd
RUN useradd -m mango
RUN echo 'mango:mango' | chpasswd

EXPOSE 22
CMD    ["/usr/sbin/sshd", "-D"]