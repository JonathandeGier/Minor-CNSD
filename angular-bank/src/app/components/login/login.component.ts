import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public username: string;
  public password: string;

  public loginSuccessfull = true;

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  login(): void {
    const success = this.loginService.login(this.username, this.password);
    if (success) {
      this.loginSuccessfull = true;
      this.router.navigate(['/']);
    } else {
      this.loginSuccessfull = false;
    }
  }

}
