import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TerminalModelService } from 'src/app/services/terminal-model/terminal-model.service';
import { ResponseEntityData } from 'src/app/models/ResponseEntityData';
import { TerminalModel } from 'src/app/models/TerminalModel';

@Component({
  selector: 'app-terminal-model',
  templateUrl: './terminal-model.component.html',
  styleUrls: ['./terminal-model.component.sass']
})
export class TerminalModelComponent implements OnInit {
  models: TerminalModel[] = [];
  searchQuery: string = '';
  filteredModels: TerminalModel[] = [];
  totalCount: number = 0;

  constructor(private router: Router, private modelService: TerminalModelService) {}

  ngOnInit(): void {
    // Fetch initial model data
    this.loadModels();
  }

  loadModels(): void {
    // Simulate fetching data from a service
    this.modelService.getAllTerminalModels().subscribe((response: ResponseEntityData) => {
      this.models = response.data as TerminalModel[];
      this.filteredModels = [...this.models];
      this.totalCount = this.models.length;
    }, (error: any) => {
      console.error('Error loading models:', error);
    });
  }

  onSearch(): void {
    if (!this.searchQuery) {
      this.filteredModels = [...this.models];
    } else {
      this.filteredModels = this.models.filter(model =>
        model.modelname.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
    this.totalCount = this.filteredModels.length;
  }

  onReset(): void {
    this.searchQuery = '';
    this.filteredModels = [...this.models];
    this.totalCount = this.models.length;
  }

  onCreate(): void {
    // Navigate to the create model page
    this.router.navigate(['/layout/terminal-models/create']);
  }
}