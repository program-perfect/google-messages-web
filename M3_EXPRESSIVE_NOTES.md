# Material 3 Expressive rewrite

This branch moves the app toward a Material 3 Web Expressive visual system.

Implemented in this branch:

- Replaced the neutral blue theme with a lavender / violet / coral expressive token layer.
- Added app-level Material Web component token overrides for FABs, filled text fields, filter chips, lists, and menus.
- Changed the shell into a softer glass / rounded panel layout.
- Reworked the sidebar header, search field, filters, and primary FAB to use Material Web components.
- Reworked the chat window app bar to use Material Web icon buttons.
- Switched Material Web loading from the full `all.js` bundle to targeted component registration.
- Updated JSX declarations for Material Web components used by the app.

Not yet finished:

- Composer and message bubble conversion to Material Web primitives still needs a follow-up pass.
- Conversation rows currently keep a simplified safe fallback and should be restored to full avatar / timestamp / unread metadata styling in the next commit.
- Build was not run in this environment.
