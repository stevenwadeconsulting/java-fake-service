APP_NAME=java-fake-service

QUAY_REPO=swade1987
QUAY_USERNAME=swade1987+trainer
QUAY_PASSWORD?="unknown"

CIRCLE_BUILD_NUM?="unknown"

# Construct the image tag.
VERSION=1.1.$(CIRCLE_BUILD_NUM)

# Construct docker image name.
IMAGE = quay.io/$(QUAY_REPO)/$(APP_NAME):$(VERSION)

build:
	docker build \
	--build-arg git_repository=`git config --get remote.origin.url` \
	--build-arg git_branch=`git rev-parse --abbrev-ref HEAD` \
	--build-arg git_commit=`git rev-parse HEAD` \
	--build-arg built_on=`date -u +"%Y-%m-%dT%H:%M:%SZ"` \
	-t $(IMAGE) .

login:
	docker login -u $(QUAY_USERNAME) -p $(QUAY_PASSWORD) quay.io

push:
	docker push $(IMAGE)
	docker rmi $(IMAGE)

logout:
	docker logout