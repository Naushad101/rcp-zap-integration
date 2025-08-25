export interface TerminalType {
    id?: number;
    type: string;
    status: number;
    deleted?: number; // Optional, used for soft delete
  }