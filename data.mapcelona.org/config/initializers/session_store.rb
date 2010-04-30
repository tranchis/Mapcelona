# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_data.mapcelona.org_session',
  :secret      => 'accd144e63182e3074035a786887352607fa19f6efcaa04c9a6ba8852064037e55b85db2de53e06d992f87fd35d2327375c33acc7a8ad057203e4812abb2e63e'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
