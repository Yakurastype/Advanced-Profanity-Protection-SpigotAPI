# Advanced-Profanity-Protection-SpigotAPI
Advanced Profanity Protection is a webhook-supported profanity filter; it can apply penalties based on categories and detect censorship.

⋆ Version: 1.16.5 < all

⋆ If you want to interpret a word as swearing only in its plain form, put a '*' sign at the end of the word (e.g. fuck*)

★ FEATURES
  - '/pp reload' command
  - Discord webhook support
  - Censorship detector
  - Permission Bypass
  - Permission / OP ingame LOGS
  - Console Logs
  - Catagories words / punishment
  - whitelist words system
  - Ability to adjust censorship hardness
  - Cool Logs

★ TODO
  - Language System

STANDART Config.yml:
```yaml
catagories:
  Sexuality:
    wordlist:
      - gay
      - LGBTQ+
    punishment: tempmute %player% 3h Sexuality
  Slang:
    wordlist:
      - stupid
    punishment: tempmute %player% 30m Slang

whitelist-words:
  - EXAMPLE_WORD

webhook:
  enabled: false
  url: "YOUR WEBHOOK"

censor:
  chance: 1.0

administrator:
  permissions: poenasaga_yetkili
```

