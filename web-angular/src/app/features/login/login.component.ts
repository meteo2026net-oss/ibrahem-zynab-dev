import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth.service';

type LoginMode = 'login' | 'register' | 'reset';

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
  mode: LoginMode = 'login';
  error = '';
  notice = '';
  submitting = false;

  async submit(): Promise<void> {
    this.error = '';
    this.notice = '';
    if (!this.auth.isConfigured) {
      this.error = 'لم يتم ضبط Firebase Web API Key. أضفه إلى public/runtime-config.js بعد إعداد تطبيق الويب في Firebase.';
      return;
    }
    if (!this.email.trim()) {
      this.error = 'أدخل البريد الإلكتروني.';
      return;
    }
    if (this.mode !== 'reset' && !this.password) {
      this.error = 'أدخل كلمة المرور.';
      return;
    }
    if (this.mode === 'register' && this.password.length < 6) {
      this.error = 'يجب ألا تقل كلمة المرور عن 6 أحرف.';
      return;
    }

    this.submitting = true;
    try {
      if (this.mode === 'reset') {
        await this.auth.requestPasswordReset(this.email);
        this.notice = 'إذا كان البريد مسجلاً، فستصلك رسالة تتضمن خطوات إعادة تعيين كلمة المرور.';
        return;
      }

      const ok = this.mode === 'register'
        ? await this.auth.register(this.email, this.password)
        : await this.auth.login(this.email, this.password);
      if (!ok) {
        this.error = this.mode === 'register'
          ? 'تعذر إنشاء الحساب. تحقق من البريد، وكلمة المرور، وتفعيل Email/Password في Firebase.'
          : 'تعذر تسجيل الدخول. تحقق من البيانات ومن تفعيل Email/Password في Firebase Authentication.';
        return;
      }

      if (this.mode === 'register') {
        const verificationSent = await this.auth.sendEmailVerification();
        this.notice = verificationSent
          ? 'تم إنشاء الحساب وإرسال رسالة تأكيد البريد الإلكتروني.'
          : 'تم إنشاء الحساب، لكن تعذر إرسال رسالة التأكيد الآن. يمكنك طلبها لاحقاً من إعدادات الحساب.';
      }
      await this.router.navigateByUrl('/home');
    } finally {
      this.submitting = false;
    }
  }

  setMode(mode: LoginMode): void {
    this.mode = mode;
    this.error = '';
    this.notice = '';
  }
}
