Crackd — A Habit-Forming App for NEET/JEE Aspirants

Overview
Crackd is a habit-forming Android app designed to help NEET/JEE students build strong emotional and learning discipline through daily routines, structured journeys, gamified tracking, and positive reinforcement.
This project was developed as part of an Android Developer Internship assignment.

 Features
(A) Onboarding
Loading screen with an oath: "I will be truthful to myself throughout journey and believe I am on right path and success is already on the way."
Google Sign-In authentication.
On first sign-in, students fill in:
Full Name
Mobile Number
Exam Type (NEET/JEE)
Next Attempt Year (2026/2027)
Class (10/11/12/Repeater)
Coaching Institute Name
Coaching Mode (Online/Offline)

(B) Home Tab
Display current stage in journey.
Show total reward points earned.
Show daily streak count.
Daily Task Cards:
Morning Check-in
Evening Check-in
Morning Coaching Audio
Evening Coaching Audio
( Party popper animation on task completion)
Emotional Well-being:
Full-width Mood Meter
EQ Lessons (Short videos)

(C) Journey Tab
5 stages with 5–10 collapsible task cards each:
Stage 1: 1 week
Stage 2: 1 month
Stage 3: 3 months
Stage 4: 6 months
Stage 5: 12 months
Next stage locked until previous stage is completed.
Progress tracking with toggles and checklists.
Gamified experience with stage unlocks.

(D) Discovery Tab
Share the app and earn reward points.
Redeem points for discounts.
View subscription status (Free / Premium).
Dummy Upgrade via Razorpay/UPI simulation.
Leaderboard snippet showing top users (mock data).

🏛 Architecture
MVVM (Model-View-ViewModel) architecture
Repository Pattern for clean data access
Jetpack Compose for modern UI building
Firebase Authentication (Google Sign-In)

 How to Run
Clone the repository.
Open in Android Studio.
Connect Firebase project (for Google Sign-In).
Run on emulator or physical device (minimum SDK 24+).
Start building habits! 

 Notes
Dummy payment simulated without real transactions.
Leaderboard and some data use mocked values.
The app is structured for future scalability (more features and real backend integration).

 Assumptions
Students will have an active Google Account for sign-in.
No real payment gateway is integrated; dummy flow for upgrade.
Mock images and audio are used where real content is not available.

 Conclusion
Crackd is designed with simplicity, positivity, and gamification to help NEET/JEE aspirants stay on track and motivated during their intense preparation journey. 

