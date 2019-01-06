import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bootstrap-basics',
  templateUrl: './bootstrap-basics.component.html',
  styleUrls: ['./bootstrap-basics.component.css']
})
export class BootstrapBasicsComponent {

  public visible = false;
  public visibleAnimate = false;
  alrt_dismiss = false;

  public show(): void {
    this.visible = true;
    setTimeout(() => this.visibleAnimate = true, 100);
  }

  public hide(): void {
    this.visibleAnimate = false;
    setTimeout(() => this.visible = false, 300);
  }

  public onContainerClicked(event: MouseEvent): void {
    if ((<HTMLElement>event.target).classList.contains('modal')) {
      this.hide();
    }
  }

  public alert_dismiss() {
    this.alrt_dismiss = false;
  }
}
