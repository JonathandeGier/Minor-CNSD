import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  public routes = {
    public: [
      {
        route: '/',
        label: 'Home',
        icon: 'fa fa-home',
      },
    ],
    login: [
      {
        route: '/details',
        label: 'Klantgegevens',
        icon: 'fa fa-info',
      },
    ],
  };

  public isLoggedInAttr = false;

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {

  }

  isLoggedIn(): boolean {
    this.isLoggedInAttr = this.loginService.isLoggedIn();
    return this.loginService.isLoggedIn();
  }

  getDisplayName(): string {
    return this.loginService.getDisplayName();
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['/']);
  }

}
