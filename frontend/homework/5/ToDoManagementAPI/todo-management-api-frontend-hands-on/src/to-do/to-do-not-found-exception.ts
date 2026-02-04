import { NotFoundException } from '@nestjs/common';

export class TodoNotFoundException extends NotFoundException {
  constructor(id: string) {
    super(`Todo with id ${id} not found`);
  }
}