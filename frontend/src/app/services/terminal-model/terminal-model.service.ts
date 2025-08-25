import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TerminalModel } from '../../models/TerminalModel';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class TerminalModelService {
  private readonly baseURL = 'http://localhost:8081/api/terminal-models';

  constructor(private readonly http: HttpClient) {}

  createTerminalModel(terminalModel: TerminalModel): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, terminalModel);
  }

  getAllTerminalModels(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllTerminalModelsIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  getTerminalModelById(id: number): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/${id}`);
  }

  updateTerminalModel(id: number, terminalModel: TerminalModel): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, terminalModel);
  }

  deleteTerminalModel(id: number): Observable<ResponseEntityData> {
    return this.http.delete<ResponseEntityData>(`${this.baseURL}/${id}`);
  }
}