export type ProjectStatus = 'active' | 'draft' | 'archived';
export type Platform = 'Android' | 'iOS' | 'Web' | 'Desktop';

export interface Project {
  id: string;
  name: string;
  description: string;
  status: ProjectStatus;
  phase: number;
  progress: number;
  platforms: Platform[];
  updatedAt: string;
}

export interface Requirement {
  id: string;
  text: string;
  priority: 'high' | 'medium' | 'low';
  approved: boolean;
}

export interface EntityField {
  name: string;
  type: string;
  required: boolean;
  unique: boolean;
  notes: string;
}

export interface DataEntity {
  name: string;
  fields: EntityField[];
}

export interface TestCase {
  id: string;
  title: string;
  expected: string;
  result: 'pass' | 'fail' | 'pending';
}

export interface AgentTask {
  id: string;
  agent: string;
  description: string;
  status: 'done' | 'review' | 'running' | 'queued';
}
