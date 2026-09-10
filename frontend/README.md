# Todo App Frontend (Next.js 14 App Router + TypeScript)

Minimal single-page Todo UI built with Next.js 14 (App Router) and TypeScript.

---

## Server & Environment Prerequisites

To install dependencies and build on your target server, ensure the following are installed:

1. **Node.js**: `v18.17.0` or higher (Node `20.x` LTS recommended)
   - Verify with: `node -v`
2. **npm**: `v9.x` or higher
   - Verify with: `npm -v`

---

## Dependencies (package.json)

### Production Dependencies
- `next`: `^14.2.15` (Next.js framework with App Router)
- `react`: `^18.3.1` (React UI library)
- `react-dom`: `^18.3.1` (React DOM rendering)

### Development Dependencies
- `typescript`: `^5.6.3` (Type checking and compilation)
- `@types/node`: `^20.16.11` (Node.js type definitions)
- `@types/react`: `^18.3.11` (React type definitions)
- `@types/react-dom`: `^18.3.0` (React DOM type definitions)

---

## Getting Started on the Server

### 1. Install Dependencies
```bash
npm install
```

### 2. Configure Environment Variables
Copy `.env.example` to `.env.local` and set your backend API URL:
```bash
cp .env.example .env.local
```
Content of `.env.local`:
```env
NEXT_PUBLIC_API_URL=http://localhost:8080
```

### 3. Production Build & Start
```bash
# Build production bundle
npm run build

# Start production server (default port 3000)
npm run start
```

### 4. Development Mode (Optional)
```bash
npm run dev
```

---

## Features & Implementation

- **API Integration**: Uses native `fetch()` without external HTTP libraries or state managers.
- **Base URL**: Dynamically reads `process.env.NEXT_PUBLIC_API_URL` (falls back to `http://localhost:8080`).
- **Endpoints Used**:
  - `GET /api/todos`: Fetches and lists all todos on page load.
  - `POST /api/todos`: Creates a new todo with `{ "title": "..." }`.
  - `PUT /api/todos/{id}`: Toggles completed status with `{ "title": "...", "completed": boolean }`.
  - `DELETE /api/todos/{id}`: Deletes a todo.
- **Styling**: Minimal, responsive styling via clean CSS in `app/globals.css`.
- **TypeScript**: Strict type definitions for todos and React event handlers.
