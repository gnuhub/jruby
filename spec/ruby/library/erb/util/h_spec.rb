require 'erb'
require File.expand_path('../../../../spec_helper', __FILE__)
require File.expand_path('../shared/html_escape', __FILE__)

describe "ERB::Util.h" do
  it_behaves_like :erb_util_html_escape, :h
end
