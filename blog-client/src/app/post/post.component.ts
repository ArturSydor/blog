import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../post.service";
import {PostPayload} from "../add-post/post-payload";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  post: PostPayload;
  postId: Number;

  constructor(private router: ActivatedRoute, private postService: PostService) { }

  ngOnInit(): void {
    this.router.params.subscribe(params =>{
      this.postId = params['id'];
    })

    this.postService.getOne(this.postId).subscribe( (data:PostPayload) => {
      this.post = data;
    }, error => {
      console.error('Failure in response: ' + error);
    })
  }

}
