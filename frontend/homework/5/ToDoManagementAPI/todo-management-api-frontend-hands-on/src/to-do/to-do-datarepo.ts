import { Todo } from './todo';

export const TODO_LIST: Todo[] = [
  {
    id: '1',
    title: 'Learn NestJS basics',
    description: 'Go through modules, controllers, and services',
    completed: false,
    createdAt: new Date('2026-02-01T09:00:00.000Z'),
    updatedAt: new Date('2026-02-01T09:00:00.000Z'),
  },
  {
    id: '2',
    title: 'Build Todo API',
    description: 'Implement CRUD operations for todos',
    completed: true,
    createdAt: new Date('2026-01-28T14:30:00.000Z'),
    updatedAt: new Date('2026-01-30T10:15:00.000Z'),
  },
  {
    id: '3',
    title: 'Write DTO validations',
    description: 'Add class-validator decorators',
    completed: false,
    createdAt: new Date('2026-02-02T08:45:00.000Z'),
    updatedAt: new Date('2026-02-02T08:45:00.000Z'),
  },
  {
    id: '4',
    title: 'Refactor service layer',
    description: 'Clean up business logic in services',
    completed: true,
    createdAt: new Date('2026-01-25T11:20:00.000Z'),
    updatedAt: new Date('2026-01-27T16:40:00.000Z'),
  },
  {
    id: '5',
    title: 'Add Swagger docs',
    description: 'Document Todo endpoints using Swagger',
    completed: false,
    createdAt: new Date('2026-02-03T12:10:00.000Z'),
    updatedAt: new Date('2026-02-03T12:10:00.000Z'),
  },
];
