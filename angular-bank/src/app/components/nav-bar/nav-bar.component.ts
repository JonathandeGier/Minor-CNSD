import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  public routes = [
    {
      route: '/',
      label: 'Home',
      icon: 'fa fa-home',
      visible: true,
    }
  ];

  constructor(private loginService: LoginService, private router: Router) {
    router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        this.initializeRoutes();
      }
    });
  }

  ngOnInit(): void {
    this.initializeRoutes();
  }

  private initializeRoutes(): void {
    this.routes = [
      {
        route: '/',
        label: 'Home',
        icon: 'fa fa-home',
        visible: true,
      },
      {
        route: '/details',
        label: 'Klantgegevens',
        icon: 'fa fa-info',
        visible: this.isLoggedIn(),
      },
    ];
  }

  isLoggedIn(): boolean {
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
