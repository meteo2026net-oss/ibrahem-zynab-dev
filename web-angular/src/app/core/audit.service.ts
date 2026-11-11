import { Injectable, signal, effect } from '@angular/core';

export interface AuditEntry {
  id: string;
  at: string;
  actor: string;
  action: string;
  target: string;
  result: 'success' | 'denied' | 'error';
  meta?: string;
}

const KEY = 'iad-audit-v1';

@Injectable({ providedIn: 'root' })
export class AuditService {
  readonly entries = signal<AuditEntry[]>(this.load());

  constructor() {
    effect(() => {
      try {
        localStorage.setItem(KEY, JSON.stringify(this.entries().slice(0, 200)));
      } catch { /* ignore */ }
    });
  }

  private load(): AuditEntry[] {
    try {
      const raw = localStorage.getItem(KEY);
      return raw ? JSON.parse(raw) : [];
    } catch {
      return [];
    }
  }

  log(actor: string, action: string, target: string, result: AuditEntry['result'] = 'success', meta?: string): void {
    const entry: AuditEntry = {
      id: 'a' + Date.now(),
      at: new Date().toISOString(),
      actor,
      action,
      target,
      result,
      meta,
    };
    this.entries.update((list) => [entry, ...list].slice(0, 200));
  }
}
