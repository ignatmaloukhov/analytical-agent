service docker stop
rm ~/.docker/config.json
service docker start

docker login -u "imalouhov" -p "***"

docker compose up -d --build

docker system prune -a --volumes


Docker Hub (docker.io)      Почти аналогично официальному, но менее защищено от подложных образов	Многие официальные образы публикуются здесь, но были случаи компрометации пользовательских образов
mirror.gcr.io (Google)	    Безопасно, но это зеркало, оригинал не гарантируется	Использовать как fallback
public.ecr.aws (Amazon)	    Безопасно, но также зеркало
quay.io (RedHat)	        Безопасно, зеркало
ghcr.io (GitHub)	        Безопасно, зеркало
dockerhub.timeweb.cloud
dockerhub1.beget.com


https://medium.com/@jefsterfarlei/sprinfboot-elk-docker-en-md-beb868e69189
https://www.baeldung.com/ops/elk