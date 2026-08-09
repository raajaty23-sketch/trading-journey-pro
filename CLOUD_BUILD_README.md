# Trading Journey Pro V3 — Persistent Android Storage

**Starting capital: ₹25,000**

V3 fixes the Chrome/localStorage persistence problem.

### Storage
Trades and daily reviews are stored in an Android SQLite database:
`trading_journey_v3.db`

The journal is therefore independent of Chrome. Closing Chrome has no effect on app data because the installed Android app has its own storage.

### Phone-only cloud build
This project includes `.github/workflows/build-apk.yml`.

1. Create/sign in to GitHub on your phone.
2. Create a new repository.
3. Upload the contents of this ZIP preserving folders.
4. Open **Actions** → **Build Trading Journey Pro APK**.
5. Tap **Run workflow**.
6. When it finishes, download **Trading-Journey-Pro-debug**.
7. Extract `app-debug.apk` and install it.

### V3 features
- ₹25,000 capital
- Persistent Android SQLite storage
- Dashboard/equity curve
- Risk calculator
- Setup analytics
- Psychology/execution tracking
- Early exit, re-entry, added-lot and moved-SL tracking
- Entry/exit screenshots
- Daily review
