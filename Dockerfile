FROM python:3.9.18-alpine3.18

WORKDIR /app

ENV PYTHONDONTWRITEBYTECODE 1

ENV PYTHONUNBUFFERED 1

RUN apk update

COPY requirements.txt /app/requirements.txt

RUN pip install --upgrade pip

RUN pip install --no-cache-dir -r requirements.txt

COPY . .