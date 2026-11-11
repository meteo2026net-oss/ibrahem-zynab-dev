import { Component, inject, computed } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  private readonly store = inject(ProjectService);

  readonly project = this.store.activeProject;
  readonly projectsCount = computed(() => this.store.projects().length);
  readonly approvedReqs = computed(
    () => this.store.requirements().filter((r) => r.approved).length
  );
  readonly totalReqs = computed(() => this.store.requirements().length);
  readonly testsPass = computed(
    () => this.store.tests().filter((t) => t.result === 'pass').length
  );
  readonly testsTotal = computed(() => this.store.tests().length);

  readonly centers = [
    { path: '/projects', label: 'المشاريع', icon: '📁', desc: 'إدارة وإنشاء المشاريع' },
    { path: '/analysis', label: 'التحليل', icon: '🔍', desc: 'تحويل الفكرة لمتطلبات' },
    { path: '/design', label: 'التصميم', icon: '🎨', desc: 'واجهات ومعاينة متعددة المنصات' },
    { path: '/functions', label: 'الوظائف', icon: '⚡', desc: 'ربط العناصر بالمنطق' },
    { path: '/data', label: 'البيانات', icon: '🗄️', desc: 'الكيانات والعلاقات' },
    { path: '/agents', label: 'الوكلاء', icon: '🤖', desc: 'مهام الذكاء الاصطناعي' },
    { path: '/build', label: 'البناء', icon: '📦', desc: 'توليد الكود والحزم' },
    { path: '/test', label: 'الاختبار', icon: '🧪', desc: 'خطط ونتائج الاختبار' },
    { path: '/release', label: 'الإطلاق', icon: '🚀', desc: 'النشر والإصدارات' },
    { path: '/monitor', label: 'المراقبة', icon: '📊', desc: 'الأداء والأخطاء' },
    { path: '/security', label: 'الأمان', icon: '🔒', desc: 'الأدوار والصلاحيات' },
    { path: '/reports', label: 'التقارير', icon: '📈', desc: 'التقدم والجودة' },
  ];
}
