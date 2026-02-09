provider "aws" {
  region = "ap-southeast-1"
}

terraform {
  backend "s3" {
    bucket         = "terraform-state"
    key            = "serverless-counter/terraform.tfstate"
    region         = "ap-southeast-1"
    dynamodb_table = "devesh-terraform-lock"
    encrypt        = true
  }
}