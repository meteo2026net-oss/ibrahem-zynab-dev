import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  readonly auth = inject(AuthService);

  email = '';
  password = '';
  name = '';
  error = '';

  submit(): void {
    this.error = 'تسجيل الدخول غير متاح حالياً: لم يتم إعداد مزود هوية موثوق بعد. لم يتم إنشاء جلسة تجريبية.';
  }
}
