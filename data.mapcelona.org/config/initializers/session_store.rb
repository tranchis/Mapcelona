# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_data.mapcelona.org_session',
  :secret      => 'd2e2ca6b565e8bc2c1675974c73ba797b3613e9615ed7ee0f201a488d60f4b1cb9f2a9ec71000d7162b09b25f85d7f2965e21b42c475d7d66d56700431f2b05e'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store