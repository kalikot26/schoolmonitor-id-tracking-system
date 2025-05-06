# SchoolMonitor – Campus ID Verification System

📅 Grade 12 Research Project (2018–2019)  
💡 Goal: Prevent fake IDs and improve school monitoring through digital verification and notification

## 🔍 Overview
SchoolMonitor is a prototype desktop application developed as a research project to address ID fraud on school campuses. It simulates scanning student IDs to verify their identity and log their entrance — with a proposed (but unimplemented) feature to send SMS notifications to parents.

## 🧰 Technologies
- Java (NetBeans IDE)
- Swing GUI
- Multi-role UI access (Admin, Teacher, Guard, Super Admin)
- Manual student/teacher registration and data editing
- Barcode/QR scanning concept (planned)
- SMS notification (proposed via API, not implemented)

## 📂 Key Features
- Login UI for different user types
- Student, Teacher, and Admin registration forms
- Ability to view/edit user data
- Basic design assets and audio feedback (e.g., siren.wav)
- Role-specific dashboards (GuardUI, TeacherUI, etc.)

## 🚧 Limitations
- SMS API integration was planned but not completed due to technical constraints at the time.
- No real database integration — data persistence may be limited to runtime or flat files.

## 📄 License
MIT License – see [LICENSE](LICENSE)
