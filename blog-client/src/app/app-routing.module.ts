import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegisterComponent} from "./auth/register/register.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegisterSuccessComponent} from "./auth/register-success/register-success.component";
import {HomeComponent} from "./home/home.component";
import {AddPostComponent} from "./add-post/add-post.component";
import {PostComponent} from "./post/post.component";
import {AuthGuard} from "./auth.guard";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'posts/:id', component: PostComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register-success', component: RegisterSuccessComponent},
  {path: 'home', component: HomeComponent},
  {path: 'add-post', component: AddPostComponent, canActivate : [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
