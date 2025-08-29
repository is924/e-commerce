import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users-list.component.html'
})
export class UsersListComponent {
  private userService = inject(UserService);

  users: any[] = [];
  loading = true;
  error = '';

  ngOnInit() {
    this.load();
  }

  load() {
    this.loading = true;
    this.userService.listAll().subscribe({
      next: (res) => { this.users = res; this.loading = false; },
      error: () => { this.error = 'Failed to load users'; this.loading = false; }
    });
  }

  toggleActive(user: any) {
    this.userService.setActive(user.userId, !user.active).subscribe({
      next: (res: any) => { user.active = res.active; },
      error: () => alert('Failed to update status')
    });
  }

  deleteUser(userId: number) {
    if (!confirm('Delete this user?')) return;
    this.userService.delete(userId).subscribe({
      next: () => { this.users = this.users.filter(u => u.userId !== userId); },
      error: () => alert('Failed to delete user')
    });
  }
}


