# StudentCardApp - Multi-Screen Campus Portal & Digital ID

An Android application featuring simulated authentication, modular student dashboard, digital identity card, and profile management. Built using modern Android architecture, Material Design 3, explicit Intents, and multiple layout paradigms (ConstraintLayout, LinearLayout, GridLayout).

---

## 📱 Implemented Screens & Architecture

| # | Activity | Purpose | Layout Demonstrated | Key UI Elements |
|---|---|---|---|---|
| 1 | `SplashActivity` | App branding and entry splash (~2s delay) | **ConstraintLayout** | App Logo, Title, Slogan, Loading Indicator |
| 2 | `LoginActivity` | Email & password authentication | **LinearLayout** (Vertical Form) | Email field, password field, Sign In button, Forgot Password link, Register link |
| 3 | `RegisterActivity` | Student account creation | **ConstraintLayout** | Full name, email, password, confirm password, Register button, Back to Login link |
| 4 | `ForgotPasswordActivity` | Simulated password recovery | **LinearLayout** (Vertical Form) | Email input, "Send Reset Link" button, Back to Login link |
| 5 | `ResetPasswordActivity` | Password update screen | **LinearLayout** (Vertical Form) | New password, confirm password, Update button, Back to Login link |
| 6 | `DashboardActivity` | Central student services portal | **GridLayout** (2 Columns) | Welcome greeting with dynamic student name, 6 interactive module cards (`MaterialCardView`) |
| 7 | `ProfileActivity` | Student details & session management | **ConstraintLayout** | Avatar, student name, email, ID, department, session, "View ID Card" button, Logout button |
| 8 | `StudentCardActivity` | Digital Student ID Card module | **ConstraintLayout** | Official campus header, EMV smart chip graphic, student photo frame, dynamic details, active badge, simulated QR code |

---

## 🧭 Navigation Flow

```
SplashActivity (Launcher, ~2s auto-delay)
       │
       ▼
  LoginActivity ────────────► RegisterActivity
       │        ◄──────────── (Success / Back)
       │
       ├────────────────────► ForgotPasswordActivity
       │                      │ (Valid email)
       │                      ▼
       │                    ResetPasswordActivity
       │        ◄──────────── (Success / Back)
       │
       ▼ (Valid Credentials)
DashboardActivity ──────────► StudentCardActivity
       │                     (Back navigation)
       │
       ├────────────────────► ProfileActivity
       │                      │
       │                      ├─► StudentCardActivity
       │                      │
       │                      └─► LoginActivity (Logout; clears back stack)
```

### Explicit Intents Only
Every transition uses standard explicit intents:
```java
Intent intent = new Intent(CurrentActivity.this, TargetActivity.class);
startActivity(intent);
```
Logout clears the task stack:
```java
Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
startActivity(intent);
finish();
```

---

## 🔑 Demo & Test Credentials

The application provides simulated local authentication. You can sign in immediately using the hardcoded credentials below or create a new account via the **Register** screen:

| Field | Demo Credential |
|---|---|
| **Email** | `student@example.com` |
| **Password** | `Pass@1234` |
| **Default Student Name** | Alex Morgan |
| **Default Registration ID**| STU-2026-8942 |
| **Default Program** | Computer Science & IT |

> **Note on Simulated Persistence:**
> When you register a new account on the Register screen, credentials and names are stored locally in `SharedPreferences`. You can immediately log into your new account from `LoginActivity`. Password resets on `ResetPasswordActivity` also update the stored credentials.

---

## 🛠️ Technical Requirements & Highlights

### 1. Mandatory Layout Paradigms
- **ConstraintLayout**: Used in `activity_splash.xml`, `activity_register.xml`, `activity_profile.xml`, and `activity_student_card.xml`. Demonstrates responsive constraints and chain styles.
- **LinearLayout**: Used in `activity_login.xml`, `activity_forgot_password.xml`, and `activity_reset_password.xml`. Demonstrates clean vertical form layouts wrapped in `ScrollView` for small screens.
- **GridLayout**: Used in `activity_dashboard.xml` with `android:columnCount="2"` and `android:rowCount="3"` to display the 6 campus service modules in responsive cards.
- **XML Layout Comments**: Every layout XML contains an explicit comment at the top declaring which layout paradigm is demonstrated.

### 2. Input Controls & Robust Validation
- **Input Types**:
  - Email: `android:inputType="textEmailAddress"`
  - Password: `android:inputType="textPassword"` with Material Design toggle visibility (`app:endIconMode="password_toggle"`)
  - Name: `android:inputType="textPersonName"`
- **Validation**:
  - Email: non-empty, matches `Patterns.EMAIL_ADDRESS`
  - Password: non-empty, minimum 6 characters
  - Name: non-empty
  - Confirm Password: non-empty, matches password
  - Visible error feedback with `setError()` on `TextInputLayout` and snackbars/toasts on invalid login

### 3. Resources & Themes (Light Mode, Dark Mode & Multi-language)
- **Zero Hardcoded Strings**: All user-visible strings are defined in `res/values/strings.xml`.
- **Alternative Language Support (Urdu)**: Complete localized string definitions provided in `res/values-ur/strings.xml`.
- **Light & Dark Mode**:
  - `res/values/colors.xml` and `res/values-night/colors.xml` define symmetric palette tokens (`primary`, `surface`, `background`, `text_primary`, `text_secondary`, `error`, etc.).
  - `res/values/themes.xml` and `res/values-night/themes.xml` reference these color tokens for smooth Day/Night mode switching.
- **Vector Drawables**: 100% vector drawables (`ic_school_logo`, `ic_student_card`, `ic_courses`, `ic_attendance`, `ic_results`, `ic_profile`, `ic_settings`, `ic_email`, `ic_lock`, `ic_person`, `ic_logout`, `ic_qr_code`, `ic_check_circle`).
- **Dimens**: Centralized spacing and typography in `res/values/dimens.xml`.

---

## 🚀 How to Run the Project

### Prerequisites
- **Android Studio** (Koala / Ladybug / Meerkat or newer)
- **Android SDK Platform 34+ (API 35/37 supported)**
- **JDK 17+ or 21+** (bundled Android Studio JBR works out-of-the-box)

### Steps
1. Clone or open the `StudentCardApp` project directory in **Android Studio**.
2. Allow Gradle sync to complete.
3. Select an Android Emulator or connected physical device (API 24 or higher).
4. Click **Run** (`Shift + F10`) or execute from PowerShell/Terminal:
   ```powershell
   ./gradlew assembleDebug
   ```
5. Install and launch on device:
   ```powershell
   ./gradlew installDebug
   ```

---

## 📋 Final Verification Checklist

- [x] All 7 core activities + Student Card activity created and registered in `AndroidManifest.xml`
- [x] `SplashActivity` is configured as the main launcher (`MAIN` & `LAUNCHER`)
- [x] Explicit Intents used exclusively for all navigation
- [x] Clear back-stack navigation on Logout (`FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK`)
- [x] All three required layout types clearly demonstrated (`ConstraintLayout`, `LinearLayout`, `GridLayout`)
- [x] XML comments at the top of each layout declaring layout type
- [x] Validated input fields with proper `inputType`, `TextInputLayout`, and `setError()`
- [x] All user-visible text stored in `res/values/strings.xml`
- [x] Bonus `res/values-ur/strings.xml` included for multi-language support
- [x] Light and Dark mode color/theme definitions (`values/colors.xml` and `values-night/colors.xml`)
- [x] Clean commit history and Android `.gitignore` included

