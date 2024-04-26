import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Task } from './task';
import { WebsocketService } from './websocket.service';
import { CommonModule, } from '@angular/common';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,CommonModule,ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit{

  title = 'real-dashboard-client';

  tasks: Task[] = [];

  form: FormGroup = new FormGroup({
    name: new FormControl<string>('', Validators.required),
    days: new FormControl<number>(0 , Validators.required)
  });

  constructor(private webSocketService: WebsocketService) {
  }

  ngOnInit(): void {
    this.webSocketService.listen(task => {
      this.tasks.push(task);
    });
  }

  add(name: string, days: number): void {
    const task: Task = {
      name: name,
      days: days
    };
    this.webSocketService.send(task);
  }

  click(): void{
    this.add(this.form.value.name, this.form.value.days);
    this.form.reset({});
  }


}
