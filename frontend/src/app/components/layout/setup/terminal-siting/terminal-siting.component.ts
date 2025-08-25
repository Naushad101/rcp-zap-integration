import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TerminalSitingService } from 'src/app/services/terminal-siting/terminal-siting.service';
import { ResponseEntityData } from 'src/app/models/ResponseEntityData';
import { TerminalSiting } from 'src/app/models/TerminalSiting';

@Component({
  selector: 'app-terminal-siting',
  templateUrl: './terminal-siting.component.html',
  styleUrls: ['./terminal-siting.component.sass']
})
export class TerminalSitingComponent implements OnInit {
  sitings: TerminalSiting[] = [];
  searchQuery: string = '';
  filteredSitings: TerminalSiting[] = [];
  totalCount: number = 0;

  constructor(private router: Router, private sitingService: TerminalSitingService) {}

  ngOnInit(): void {
    // Fetch initial siting data
    this.loadSitings();
  }

  loadSitings(): void {
    // Simulate fetching data from a service
    this.sitingService.getAllTerminalSitings().subscribe((response: ResponseEntityData) => {
      this.sitings = response.data as TerminalSiting[];
      this.filteredSitings = [...this.sitings];
      this.totalCount = this.sitings.length;
    }, (error: any) => {
      console.error('Error loading sitings:', error);
    });
  }

  onSearch(): void {
    if (!this.searchQuery) {
      this.filteredSitings = [...this.sitings];
    } else {
      this.filteredSitings = this.sitings.filter(siting =>
        siting.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
    this.totalCount = this.filteredSitings.length;
  }

  onReset(): void {
    this.searchQuery = '';
    this.filteredSitings = [...this.sitings];
    this.totalCount = this.sitings.length;
  }

  onCreate(): void {
    // Navigate to the create siting page
    this.router.navigate(['/layout/terminal-sitings/create']);
  }
}