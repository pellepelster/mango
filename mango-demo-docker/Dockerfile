FROM     ubuntu:12.04
MAINTAINER Christian Pelster <pelle@pelle.io>

# make sure the package repository is up to date
RUN apt-get update

RUN apt-get install -y openjdk-7-jdk

RUN apt-get install -y openssh-server
RUN mkdir /var/run/sshd

# make the ssh port available
EXPOSE 22
ADD ssh/id_rsa.pub /root/.ssh/authorized_keys
RUN chown root:root /root/.ssh/authorized_keys

ADD eclipse /root/eclipse/

# start the ssh daemon
CMD ["/usr/sbin/sshd", "-D"]