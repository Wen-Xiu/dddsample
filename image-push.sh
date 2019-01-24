#!/usr/bin/env bash

docker images
    apk -v --update add \
        python \
        py-pip \
        groff \
        less \
        mailcap \
        && \
    pip install --upgrade awscli==1.14.5 s3cmd==2.0.1 python-magic && \
    apk -v --purge del py-pip && \
    rm /var/cache/apk/*

docker build -f Dockerfile -t 494526681395.dkr.ecr.ap-southeast-1.amazonaws.com/dddsample .

docker images|grep dddsample

login_command=$(aws ecr get-login --registry-ids 494526681395 --region ap-southeast-1 --no-include-email)

eval "${login_command} | sed 's|https://||'"

docker push 494526681395.dkr.ecr.ap-southeast-1.amazonaws.com/dddsample
