# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_data.mapcelona.org_session',
  :secret      => '75f811a82ef1e39626dd87e07ea089fd879a11e988f94152ae4ade444de9b0b259568b6c062350aa0acee79b414a62f6fee6250a24635271a3762a9648815356'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
