import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
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
  private readonly router = inject(Router);

  email = '';
  password = '';
  error = '';
  submitting = false;

  async submit(): Promise<void> {
    this.error = '';
    if (!this.auth.isConfigured) {
      this.error = 'لم يتم ضبط Firebase Web API Key. أضفه إلى public/runtime-config.js بعد إعداد تطبيق الويب في Firebase.';
      return;
    }
    if (!this.email.trim() || !this.password) {
      this.error = 'أدخل البريد الإلكتروني وكلمة المرور.';
      return;
    }

    this.submitting = true;
    try {
      const ok = await this.auth.login(this.email, this.password);
      if (ok) {
        await this.router.navigateByUrl('/home');
      } else {
        this.error = 'تعذر تسجيل الدخول. تحقق من البيانات ومن تفعيل Email/Password في Firebase Authentication.';
      }
    } finally {
      this.submitting = false;
    }
  }
}
