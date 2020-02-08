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
	--build-arg VERSION=$(VERSION) \
	-t $(IMAGE) .

login:
	docker login -u $(QUAY_USERNAME) -p $(QUAY_PASSWORD) quay.io

push:
	docker push $(IMAGE)
	docker rmi $(IMAGE)

logout:
	docker logout


# ========================================================================================
# DOCKER TASKS
# ========================================================================================

run-docker:
	docker run -p -d 8080:8080 $(IMAGE)

compose-build:
	docker-compose build --no-cache

compose-up:
	docker-compose up -d

compose-down:
	docker-compose down

# ========================================================================================
# KUBERNETES TASKS
# ========================================================================================

cluster:
	kind create cluster --name fake-service

rolling-update:
	kubectl apply -f kubernetes/rolling-update

port-forward:
	kubectl port-forward svc/java-fake-service 8080:80