export type ProjectStatus = 'active' | 'draft' | 'archived';
export type Platform = 'Android' | 'iOS' | 'Web' | 'Desktop';
export type Priority = 'high' | 'medium' | 'low';
export type TestResult = 'pass' | 'fail' | 'pending';
export type AgentStatus = 'done' | 'review' | 'running' | 'queued';

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
  priority: Priority;
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
  result: TestResult;
}

export interface AgentTask {
  id: string;
  agent: string;
  description: string;
  status: AgentStatus;
}

export interface FunctionStep {
  id: number;
  title: string;
  detail: string;
  kind: 'check' | 'action' | 'success' | 'error';
}

export interface DesignPage {
  id: string;
  name: string;
  platform: Platform;
}

export interface AppSettings {
  language: 'ar' | 'en';
  timezone: string;
  notifyBuild: boolean;
  notifyRelease: boolean;
  notifyComments: boolean;
}

export interface TeamMember {
  name: string;
  role: string;
  badge: string;
}

export interface CommentItem {
  author: string;
  target: string;
  text: string;
  status: 'open' | 'approved' | 'changes';
}

export interface ReleaseInfo {
  version: string;
  notes: string;
  checklist: { label: string; done: boolean }[];
  lastPublished?: string;
}

export interface AppState {
  projects: Project[];
  activeProjectId: string;
  ideaText: string;
  requirements: Requirement[];
  analysisDone: boolean;
  entities: DataEntity[];
  tests: TestCase[];
  agentTasks: AgentTask[];
  buildLog: string[];
  lastApk: { name: string; size: string; ready: boolean };
  functionSteps: FunctionStep[];
  functionNatural: string;
  designPages: DesignPage[];
  currentDesignPageId: string;
  settings: AppSettings;
  team: TeamMember[];
  comments: CommentItem[];
  release: ReleaseInfo;
  monitorErrors: { type: string; screen: string; message: string; count: number }[];
  roles: { role: string; permissions: string }[];
}
