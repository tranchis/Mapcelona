require 'test_helper'

class PolygonsControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:polygons)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create polygon" do
    assert_difference('Polygon.count') do
      post :create, :polygon => { }
    end

    assert_redirected_to polygon_path(assigns(:polygon))
  end

  test "should show polygon" do
    get :show, :id => polygons(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => polygons(:one).to_param
    assert_response :success
  end

  test "should update polygon" do
    put :update, :id => polygons(:one).to_param, :polygon => { }
    assert_redirected_to polygon_path(assigns(:polygon))
  end

  test "should destroy polygon" do
    assert_difference('Polygon.count', -1) do
      delete :destroy, :id => polygons(:one).to_param
    end

    assert_redirected_to polygons_path
  end
end
