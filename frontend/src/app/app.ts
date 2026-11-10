import { Component, signal, input } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [FormsModule, RouterOutlet],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  originalUrl = '';
  shortUrl = '';
  errorMessage = '';

  shortenUrl() : void{

    if (this.originalUrl.length === 0) {
      this.errorMessage = 'url field is empty';
      this.shortUrl = '';
      return;
    }

    this.errorMessage = '';
    this.shortUrl = 'Your URL: ' + this.originalUrl;
  }
}
