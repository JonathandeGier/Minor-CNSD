import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private users = [
    {
      id: 1,
      username: 'test1',
      password: 'test1',
      name: 'Test user 1',
    },
    {
      id: 2,
      username: 'test2',
      password: 'test2',
      name: 'Test user 2',
    },
    {
      id: 3,
      username: 'test3',
      password: 'test3',
      name: 'Test user 3',
    },
  ];

  private loggedInUser = null;

  constructor() { }

  getCurrentUser(): number {
    return this.loggedInUser;
  }

  getDisplayName(): string {
    return this.users.filter(value => value.id === this.loggedInUser)[0].name;
  }

  isLoggedIn(): boolean {
    return this.loggedInUser !== null;
  }

  login(username, password): boolean {
    const user = this.users.filter(value => value.username === username && value.password === password)[0];

    if (!(user === undefined || user === null)) {
      this.loggedInUser = user.id;
      return true;
    } else {
      return false;
    }
  }

  logout(): void {
    this.loggedInUser = null;
  }
}
