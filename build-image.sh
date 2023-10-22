function dev() {
  ./mvnw clean install \
  && docker build --platform linux/amd64 -t wilvermartinezg.registry.jetbrains.space/p/main/docker-images/visitors-backend:dev .
}

"$@"