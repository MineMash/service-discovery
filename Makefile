
.PHONY: controller_image
controller_image:
	docker build -t minemesh/sd-controller -f controller/Dockerfile .

