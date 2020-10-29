import { Component, OnInit } from '@angular/core';
import {PostService} from "../post.service";
import {Observable} from "rxjs";
import {PostPayload} from "../add-post/post-payload";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  posts: Observable<Array<PostPayload>>;

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.posts = this.postService.getAll();
  }

}
